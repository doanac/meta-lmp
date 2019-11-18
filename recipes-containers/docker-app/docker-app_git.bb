DESCRIPTION = "Make your Docker Compose applications reusable, and share them on Docker Hub"
HOMEPAGE = "https://github.com/docker/app"
SECTION = "devel"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=4859e97a9c7780e77972d989f0823f28"

GO_IMPORT = "github.com/docker/app"
SRC_URI = "git://${GO_IMPORT} \
	file://0001-packing-use-hub.foundries.io-cnab-app-base-for-cnab-.patch;patchdir=src/${GO_IMPORT} \
	file://0001-Mark-app-as-non-experimental.patch;patchdir=src/${GO_IMPORT} \
	file://cli-config-support-default-system-config.patch;patchdir=src/${GO_IMPORT} \
	file://791-pull-service-images.patch;patchdir=src/${GO_IMPORT} \
"
SRCREV = "012748d75405fb67b7c523f7660fca67bdc4f748"

UPSTREAM_CHECK_COMMITS = "1"
# PV = "v0.9.0-beta1"
PV = "master"

inherit go goarch

do_compile() {
	cd ${S}/src/${GO_IMPORT}
	BUILD_TAG=`git describe --always --abbrev=10`
	BUILD_COMMIT=`git rev-parse --short HEAD`
	DOCKER_APP_LDFLAGS="-X ${GO_IMPORT}/internal.GitCommit=${BUILD_COMMIT} \
		-X ${GO_IMPORT}/internal.Version=${BUILD_TAG} \
		-X ${GO_IMPORT}/internal.Experimental=off"
	mkdir -p ${B}/${GO_BUILD_BINDIR}
	${GO} build -ldflags="${DOCKER_APP_LDFLAGS}" -o ${WORKDIR}/docker-app ./cmd/docker-app
}

do_install() {
	install -d ${D}${libdir}/docker/cli-plugins
	install -m 0755 ${WORKDIR}/docker-app ${D}${libdir}/docker/cli-plugins
}

RDEPENDS_${PN}-dev += "bash"
FILES_${PN} += "${libdir}/docker/cli-plugins"
