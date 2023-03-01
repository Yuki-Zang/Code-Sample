for file in "$@"
do
  du $file 
  trimmed=$(basename $file .mp3)
  ffmpeg -i $file -acodec pcm_s16le -ar 16000 -ac 1 wav/"$trimmed".wav
done