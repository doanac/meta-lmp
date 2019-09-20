DESCRIPTION = "Management library for 32-bit MCUs"
HOMEPAGE = "https://github.com/apache/mynewt-mcumgr"
SECTION = "devel"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

GO_IMPORT = "github.com/apache/mynewt-mcumgr-cli/mcumgr"
SRC_URI = "git://github.com/apache/mynewt-mcumgr-cli"
SRCREV = "3ba8601a237b13b195a261763e83da29b8efca25"

UPSTREAM_CHECK_COMMITS = "1"
PV = "v0.0.1+git"

inherit go goarch godep

RDEPENDS_${PN}-dev += "bash"
RDEPENDS_${PN}-staticdev += "bash"
