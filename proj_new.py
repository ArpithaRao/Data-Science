import json
from textblob import TextBlob
from textblob import Word
from collections import OrderedDict

f_stop = open("stopwords",'r');

f = f_stop.read()
a = [splits for splits in f.split("\t")]
stop_words = []

for i in a:
	stop_words.append(i.split("\n")[0])
	try:
		if i.split("\n")[1]:
			stop_words.append(i.split("\n")[1])
	except:
		pass
		
f = open('canon.json', 'r')
x = json.load(f)
name = x["ProductInfo"]["Name"]
#~ name = x['array']['product_info']['title']
name = name.lower()
print name
sentence_list = []
bloblist = []
for rev in x["Reviews"]:
    bloblist.append(TextBlob(rev["Content"]))
    
for blob in bloblist:
	for sentence in blob.sentences:
		sentence_list.append(sentence)

pos_output = []
#bloblist	
for blob in sentence_list:
	pos_reviews = blob.tags
	np = blob.noun_phrases
	new = [x[0] for x in pos_reviews if x[1]=="NN" or x[1] == "NNS"]
	new = new + np
	pos_output.append(new)

def createC1(dataset):
    "Create a list of candidate item sets of size one."
    c1 = []
    for transaction in dataset:
        for item in transaction:
            if not [item] in c1:
                c1.append([item])
    c1.sort()
    #frozenset because it will be a ket of a dictionary.
    return map(frozenset, c1)


def scanD(dataset, candidates, min_support):
    "Returns all candidates that meets a minimum support level"
    sscnt = {}
    for tid in dataset:
        for can in candidates:
            if can.issubset(tid):
                sscnt.setdefault(can, 0)
                sscnt[can] += 1

    num_items = float(len(dataset))
    retlist = []
    support_data = {}
    for key in sscnt:
        support = sscnt[key] / num_items
        if support >= min_support:
            retlist.insert(0, key)
        support_data[key] = support
    return retlist, support_data


def aprioriGen(freq_sets, k):
    "Generate the joint transactions from candidate sets"
    retList = []
    lenLk = len(freq_sets)
    for i in range(lenLk):
        for j in range(i + 1, lenLk):
            L1 = list(freq_sets[i])[:k - 2]
            L2 = list(freq_sets[j])[:k - 2]
            L1.sort()
            L2.sort()
            if L1 == L2:
                retList.append(freq_sets[i] | freq_sets[j])
    return retList	

def apriori(dataset, minsupport=0.1):
    "Generate a list of candidate item sets"
    C1 = createC1(dataset)
    D = map(set, dataset)
    L1, support_data = scanD(D, C1, minsupport)
    L = [L1]
    k = 2
    while (len(L[k - 2]) > 0):
        Ck = aprioriGen(L[k - 2], k)
        Lk, supK = scanD(D, Ck, minsupport)
        support_data.update(supK)
        L.append(Lk)
        k += 1

    return L

dataset = pos_output
C1 = createC1(dataset)
D = map(set,dataset)
L1, support_data = scanD(D, C1, 0.01)
#L2 = apriori(dataset)

# Convert List of Frozen sets to normal list and removing if it is in stopword list
true_features = []
stem_dict = []
for frozen_set in L1:
	temp = list(frozen_set)[0]
	stem_word = Word(temp).lemmatize()
	if temp not in stop_words and temp not in name and stem_word not in stem_dict:
		true_features.append(temp)
		stem_dict.append(stem_word)

classified_sentences = {}
summary_sentences = {}

for feature in true_features:
	classified_sentences[feature] = {}
	classified_sentences[feature]['pos'] = []
	classified_sentences[feature]['neg'] = []
	
	summary_sentences[feature] = {}
	summary_sentences[feature]['pos'] = []
	summary_sentences[feature]['neg'] = []
	
for feature in true_features:
	for sentence in sentence_list:
		if feature in sentence:
			if sentence.sentiment[0] > 0:
				classified_sentences[feature]['pos'].append(str(sentence))
				classify = [1 for x in sentence.tags if x[1]=="PRP" or x[0].lower() == "my" or x[0].lower == "mine"]
				if len(classify) == 0:
					summary_sentences[feature]['pos'].append(str(sentence))
			if sentence.sentiment[0] < 0:
				classified_sentences[feature]['neg'].append(str(sentence))
				classify = [1 for x in sentence.tags if x[1]=="PRP"]
				if len(classify) == 0:
					summary_sentences[feature]['neg'].append(str(sentence))

total_reviews = {}
for i in summary_sentences:
	total_reviews[i] = len(summary_sentences[i]['pos']) + (len(summary_sentences[i]['neg']))

#~ new_dict = OrderedDict(sorted(total_reviews.items(), key=lambda t: t[1]))
new_dict = sorted(total_reviews.items(), key=lambda x: (-x[1], x[0]))
print new_dict

c = 10000
index = [0,0,0,0,0]
for i in range(0,5):
	c = 10000
	for j in summary_sentences[new_dict[i][0]]['pos']:
		if len(j)<c and (j not in index):
			if j.find('?') == -1 and j.find('(') == -1 and (j.startswith('The') or j.startswith('This')):
				c = len(j)
				index[i] = j

print index
index = "".join(index)

output_json = {'Summary': index, 'Features': classified_sentences}
output = open('classified.json', 'wb')	
output.write(json.dumps(output_json))
output.close
f.close
f_stop.close
