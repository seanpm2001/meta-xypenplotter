DESCRIPTION = "Init script and application for Qt XY-Plotter demo on Vybrid"

HOMEPAGE = "http://www.toradex.com"

SRCREV = "2f86957eef10d026af1efc2e6e2c5b9da88021fa"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://README.md;md5=aaa3c304de2b3a1a0e1ff13043fa4975 \
                    file://main.cpp;md5=44833ee9b5700153b40df283121647f2"

SRC_URI = " \
    git://github.com/toradex/XYPenPlotter.git;branch=with-libmcc \
    file://qtxyplotter-init \
    file://mouse.rules \
    file://keyboard.rules \
    file://fusion.conf \
    file://mcc.conf \
    file://plotter.bin \
"

SRC_URI[md5sum] = "142ef697332e0777c6d22c5bc96cc438"
SRC_URI[sha256sum] = "e4d0c005d2cb1d7c09438bfc3098eadebc08946e4fbc0655b7fc8b046de3810d"

#S = "${WORKDIR}/XYPenPlotter-${PV}"
S = "${WORKDIR}/git"

#inherit qt4x11
inherit qt4e

EXTRA_QMAKEVARS_PRE = "CONFIG+=no-webcam"

TOUCH = ' ${@base_contains("MACHINE_FEATURES", "touchscreen", "tslib tslib-calibrate tslib-tests", "",d)}'
RDEPENDS_${PN} = " \
	libqt-embedded3support4 \
	libqt-embeddeddeclarative4 \
	libqt-embeddedcore4 \
	libqt-embeddedgui4 \
	libqt-embeddedhelp4 \
	libqt-embeddedsvg4 \
	libqt-embeddedxml4 \
	qt4-embedded-fonts-ttf-dejavu \
	qt4-embedded-fonts-ttf-vera \
	qt4-embedded-plugin-imageformat-svg \
	qt4-embedded-plugin-imageformat-jpeg \
	qt4-embedded-plugin-mousedriver-tslib \
	${TOUCH} \
	qt4-embedded-assistant \
	libmcc \
	kernel-module-mcc \
	mqxboot \
"
FILES_${PN} = "/var \
               /var/cache \
	       /var/cache/xyplotter \
               ${sysconfdir} \
	       ${bindir} \
		"

RRECOMMENDS_${PN} = " \
	libqt-embeddedxmlpatterns4 \
"


do_install() {
    oe_runmake INSTALL_ROOT=${D} install
    mkdir -p ${D}/var/cache/xyplotter/
    install -d ${D}/var/cache/xyplotter/
    install -d ${D}${sysconfdir}/init.d/
    install -d ${D}${sysconfdir}/init.d/
    install -d ${D}${sysconfdir}/udev/rules.d/
    install -d ${D}${sysconfdir}/modules-load.d/
    install -m 0755 ${WORKDIR}/plotter.bin ${D}/var/cache/xyplotter/
    install -m 0755 ${WORKDIR}/qtxyplotter-init ${D}${sysconfdir}/init.d/qtxyplotter
    install -m 0644 ${WORKDIR}/mouse.rules ${D}${sysconfdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/keyboard.rules ${D}${sysconfdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/fusion.conf ${D}${sysconfdir}/modules-load.d/
    install -m 0644 ${WORKDIR}/mcc.conf ${D}${sysconfdir}/modules-load.d/
}

FILES_${PN}-dbg += "${bindir}/.debug"


inherit update-rc.d

INITSCRIPT_NAME = "qtxyplotter"
INITSCRIPT_PARAMS = "start 99 5 2 . stop 19 0 1 6 ."


# Ensure we have some plugins for some useful image formats
RRECOMMENDS_${PN} += "qt4-plugin-imageformat-gif qt4-plugin-imageformat-jpeg qt4-plugin-imageformat-tiff"

