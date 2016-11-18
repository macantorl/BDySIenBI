import sys
import urllib2

if sys.argv[1]=='org':
    url_template = 'http://rest.kegg.jp/list/organism'
    f = urllib2.urlopen(url_template).readlines()
elif sys.argv[1].find('map')!=-1:
    url_template = 'http://rest.kegg.jp/get/%s'
    f = urllib2.urlopen(url_template % sys.argv[1]).readlines()
else:
    url_template = 'http://rest.kegg.jp/list/pathway/%s'
    f = urllib2.urlopen(url_template % sys.argv[1]).readlines()

for data in f:
    print data