HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=30503fd321432fc713238f582193b78e"
DEPENDS += "flex-native bison-native bc-native dtc-native"

require recipes-bsp/u-boot/u-boot.inc

SRCREV = "96e3e686a31baf729c9086b0099908f4126f0f7b"
SRCBRANCH = "2019.10+fio"

SRC_URI = "git://github.com/foundriesio/u-boot.git;branch=${SRCBRANCH}"

PV = "v2019.10+git${SRCPV}"

S = "${WORKDIR}/git"

# Support additional u-boot classes such as u-boot-fitimage
UBOOT_CLASSES ?= ""
inherit ${UBOOT_CLASSES}

RDEPENDS_${PN}_append_sota = " u-boot-ostree-scr"

PROVIDES += "u-boot"
