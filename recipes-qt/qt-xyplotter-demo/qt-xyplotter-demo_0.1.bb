DESCRIPTION = "Init script for Qt XY-Plotter demo on Vybrid"
LICENSE = "MIT"
SRC_URI = " \
    file://qtxyplotter-init \
    file://mouse.rules \
    file://keyboard.rules \
    file://fusion.conf \
"
PR = "r0"

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

RRECOMMENDS_${PN} = " \
	libqt-embeddedxmlpatterns4 \
"

LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

do_install() {
    install -d ${D}${sysconfdir}/init.d/
    install -d ${D}${sysconfdir}/udev/rules.d/
    install -d ${D}${sysconfdir}/modules-load.d/
    install -m 0755 ${WORKDIR}/qtxyplotter-init ${D}${sysconfdir}/init.d/qtxyplotter
    install -m 0644 ${WORKDIR}/mouse.rules ${D}${sysconfdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/keyboard.rules ${D}${sysconfdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/fusion.conf ${D}${sysconfdir}/modules-load.d/
}

inherit update-rc.d allarch

INITSCRIPT_NAME = "qtxyplotter"
INITSCRIPT_PARAMS = "start 99 5 2 . stop 19 0 1 6 ."
