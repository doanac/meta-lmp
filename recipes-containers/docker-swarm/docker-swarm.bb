SUMMARY = "Automatically configure and initialize docker-swarm on first boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit allarch systemd

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://docker-swarm-initialize.service"

do_install() {
	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/docker-swarm-initialize.service ${D}${systemd_system_unitdir}
}

SYSTEMD_SERVICE_${PN} = "docker-swarm-initialize.service"
SYSTEMD_AUTO_ENABLE_${PN} = "enable"
