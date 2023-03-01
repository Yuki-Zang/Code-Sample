import os
import re
from string import Template

ffmpeg = Template('ffmpeg -i \'$idir\'/\'$ifile\'  -vcodec libx264 -crf 30 -ar 16000 -ac 1 -vf scale=426:240 videosAnnotate/$ofile.mp4')

for idir in os.listdir('.'):
    if re.match(r'Series', idir):
        for ifile in os.listdir(idir):
            ofile = re.search(r's\d+e\d+', ifile)[0]
            print(ifile, ofile, ffmpeg.substitute(idir=idir, ifile=ifile, ofile=ofile))
            res = os.system(ffmpeg.substitute(idir=idir, ifile=ifile, ofile=ofile))
