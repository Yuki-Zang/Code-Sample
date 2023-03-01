import json
import os
from prodigy.util import file_to_b64
import sys

indir = sys.argv[1]
outfile = sys.argv[2]

with open(outfile, 'w') as outf:
    for name in os.listdir(indir):
        try:
            with open(indir + '/' + name) as f:
                #            print(name)
                stuff = json.loads(f.read())
                stuff['video'] = file_to_b64(stuff['video'])
                newspans = []
                for span in stuff['audio_spans']:
                    if span['label'] == 'SEGMENT_TRUTH':
                        newspans.append({'start': span['start'], 'end': span['start'] + 5, 'label': 'SEGMENT_START'})
                        newspans.append({'start': span['end']-5, 'end': span['end'], 'label': 'SEGMENT_END'})
                    #else:
                        #newspans.append(span)
                stuff['audio_spans'] = sorted(newspans, key=lambda x: x['start'])
                json.dump(stuff, outf)
                outf.write("\n")
                #            print("\n")
        except Exception as e:
            print(e)
        
