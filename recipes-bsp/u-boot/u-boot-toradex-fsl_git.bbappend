FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://memory.patch \
          file://video.patch \
"
