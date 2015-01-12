#sudo apt-get update
#sudo apt-get install -y --force-yes ffmpeg

sudo apt-get update
sudo apt-get -y install autoconf automake build-essential libass-dev libfreetype6-dev libgpac-dev \
  libsdl1.2-dev libtheora-dev libtool libva-dev libvdpau-dev libvorbis-dev libx11-dev \
  libxext-dev libxfixes-dev pkg-config texi2html zlib1g-dev yasm libx264-dev
mkdir ~/ffmpeg_sources


wget http://ffmpeg.org/releases/ffmpeg-2.5.3.tar.bz2
tar xvf ffmpeg-2.5.3.tar.bz2
cd ffmpeg-2.5.3/ 
./configure --enable-gpl --enable-libx264
sudo make install
sudo make distclean

sudo echo "ffmpeg -framerate 30 -i %06d.png -c:v libx264 -r 30 -pix_fmt yuv420p out.mp4" > /bin/mkvid
sudo chmod +x /bin/mkvid
