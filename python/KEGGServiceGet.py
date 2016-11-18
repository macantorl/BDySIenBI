import sys
import os
import urllib2
from KGML_scrape import retrieve_kgml_to_file, retrieve_KEGG_pathway
from KGML_vis import KGMLCanvas

goal_dir = os.path.dirname(os.path.abspath(__file__))+"\..\Downloads"
pathway = retrieve_KEGG_pathway(sys.argv[1])
kgml_map = KGMLCanvas(pathway)
kgml_map.import_imagemap = True
kgml_map.draw(os.path.join(os.path.normpath(goal_dir)+'\PDF', sys.argv[1]+'.pdf'))
retrieve_kgml_to_file(sys.argv[1], os.path.join(os.path.normpath(goal_dir)+'\XML', sys.argv[1]+'.xml'))
print sys.argv[1]+'.pdf'
