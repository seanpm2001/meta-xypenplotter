#Angstrom image
DESCRIPTION = "Image based on qt4e-demo-image.bb"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58"

PV = "V2.2"
PR = "r0"

#start of the resulting deployable tarball name
IMAGE_NAME_colibri-vf = "Colibri_VF_LinuxImage-qte-xyplotter"
IMAGE_NAME = "${MACHINE}_LinuxImage-qte"

#create the deployment directory-tree
require recipes/images/trdx-image-fstype.inc

#remove interfering sysv scripts, connman systemd service
ROOTFS_POSTINSTALL_COMMAND = "for i in ${IMAGE_ROOTFS}/etc/rc0.d ${IMAGE_ROOTFS}/etc/rc1.d ${IMAGE_ROOTFS}/etc/rc2.d ${IMAGE_ROOTFS}/etc/rc3.d ${IMAGE_ROOTFS}/etc/rc4.d ${IMAGE_ROOTFS}/etc/rc5.d ${IMAGE_ROOTFS}/etc/rc6.d ${IMAGE_ROOTFS}/etc/rcS.d ; do rm -f $i/*dropbear $i/*avahi-daemon $i/*dbus-1 $i/*lxdm $i/*ntpd $i/*syslog $i/*ofono $i/*alsa-state $i/*networking $i/*udev-late-mount $i/*sendsigs $i/*save-rtc.sh $i/*umountnfs.sh $i/*portmap $i/*umountfs $i/*halt $i/*rmnologin.sh $i/*reboot; rm -f $i/*banner.sh $i/*sysfs.sh $i/*checkroot.sh $i/*alignment.sh $i/*mountall.sh $i/*populate-volatile.sh  $i/*devpts.sh  $i/*hostname.sh $i/*portmap  $i/*mountnfs.sh  $i/*bootmisc.sh ; done"

IMAGE_LINGUAS = "en-us"
#IMAGE_LINGUAS = "de-de fr-fr en-gb en-us pt-br es-es kn-in ml-in ta-in"
#ROOTFS_POSTPROCESS_COMMAND += 'install_linguas; '

DISTRO_UPDATE_ALTERNATIVES ??= ""
ROOTFS_PKGMANAGE_PKGS ?= '${@base_conditional("ONLINE_PACKAGE_MANAGEMENT", "none", "", "${ROOTFS_PKGMANAGE} ${DISTRO_UPDATE_ALTERNATIVES} update-alternatives-cworth", d)}'

CONMANPKGS ?= "connman connman-systemd connman-plugin-loopback connman-plugin-ethernet connman-plugin-wifi connman-client"
CONMANPKGS_libc-uclibc = ""

#don't install some id databases
BAD_RECOMMENDATIONS_VF_colibri_vf += " udev-hwdb "
BAD_RECOMMENDATIONS += " ${BAD_RECOMMENDATIONS_VF} "

IMAGE_INSTALL += " \
    qt-xyplotter-demo \
    libicui18n \
    xdg-utils \
    \
    initscripts \
    polkit \
    makedevs \
    mime-support \
    zeroconf \
    fbset \	
    angstrom-packagegroup-boot \
    task-basic \
    udev-extra-rules \
    ${CONMANPKGS} \
    ${ROOTFS_PKGMANAGE_PKGS} \
    timestamp-service \
    task-base-extended \
    \
    bash \
    \
    alsa-states \
"

require recipes/images/trdx-extra.inc

IMAGE_DEV_MANAGER   = "udev"
IMAGE_INIT_MANAGER  = "systemd"
IMAGE_INITSCRIPTS   = " "
IMAGE_LOGIN_MANAGER = "shadow"

export IMAGE_BASENAME = "qt-image"

inherit core-image
