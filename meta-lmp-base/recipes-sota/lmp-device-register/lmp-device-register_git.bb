SUMMARY = "Linux microPlatform OSF OTA+ device registration tool"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING.MIT;md5=838c366f69b72c5df05c96dff79b35f2"

DEPENDS = "boost curl glib-2.0 libp11 openssl"

SRCREV = "85675c3abcb109e01dd9ef6755137ec62ec13041"

SRC_URI = "git://github.com/doanac/lmp-device-register.git;protocol=https;branch=meds-updates"

LMP_DEVICE_API ?= "https://api.foundries.io/ota/devices/"
LMP_OAUTH_API ?= "https://app.foundries.io/oauth"

PACKAGECONFIG ?= "composeapp"
PACKAGECONFIG[composeapp] = "-DDOCKER_COMPOSE_APP=ON,-DDOCKER_COMPOSE_APP=OFF,"
PACKAGECONFIG[production] = "-DPRODUCTION=ON,-DPRODUCTION=OFF,"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

RDEPENDS:${PN} += "${SOTA_CLIENT}"

EXTRA_OECMAKE += "\
    -DGIT_COMMIT=${SRCREV} \
    -DHARDWARE_ID=${MACHINE} \
    -DDEVICE_API=${LMP_DEVICE_API} \
    -DOAUTH_API=${LMP_OAUTH_API} \
    -DSOTA_CLIENT=${SOTA_CLIENT} \
"
