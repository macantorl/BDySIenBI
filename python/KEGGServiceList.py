import sys
import urllib2

url_template = 'http://rest.kegg.jp/list/pathway/%s '

f = urllib2.urlopen(url_template % sys.argv[1]).readlines()

for data in f:
    print data