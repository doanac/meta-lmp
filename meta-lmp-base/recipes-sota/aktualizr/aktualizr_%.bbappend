FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

BRANCH_lmp = "master"
SRCREV_lmp = "11517ad2d2aa84e0de54e9e09b24033ad23c272f"

SRC_URI_lmp = "gitsm://github.com/foundriesio/aktualizr-lite;branch=${BRANCH};name=aktualizr \
    file://aktualizr.service \
    file://aktualizr-lite.service \
    file://aktualizr-lite.path \
    file://aktualizr-secondary.service \
    file://aktualizr-serialcan.service \
    file://10-resource-control.conf \
    ${@ d.expand("https://ats-tuf-cli-releases.s3-eu-central-1.amazonaws.com/cli-${GARAGE_SIGN_PV}.tgz;unpack=0;name=garagesign") if d.getVar('GARAGE_SIGN_AUTOVERSION') != '1' else ''} \
"

SRC_URI_append_libc-musl = " \
    file://utils.c-disable-tilde-as-it-is-not-supported-by-musl.patch \
"

PACKAGECONFIG += "${@bb.utils.filter('SOTA_EXTRA_CLIENT_FEATURES', 'fiovb', d)}"
PACKAGECONFIG[fiovb] = ",,,optee-fiovb aktualizr-fiovb-env-rollback"
PACKAGECONFIG[dockerapp] = "-DBUILD_DOCKERAPP=ON,-DBUILD_DOCKERAPP=OFF,,docker-app"
PACKAGECONFIG_append_class-target = " dockerapp"
PACKAGECONFIG_remove_class-target_riscv64 = "dockerapp"

SYSTEMD_PACKAGES += "${PN}-lite"
SYSTEMD_SERVICE_${PN}-lite = "aktualizr-lite.service aktualizr-lite.path"


# Override meta-updater because it doesn't know where to find get_version.sh
do_configure_prepend() {
    bbwarn "Hacking get_version!"
    cd ${S}
    git log -1 --format=%h | tr -d '\n' > VERSION
}

do_install_prepend() {
    bbwarn "Hacking install paths"

    # hack the path to config so aktualizr's do_install_append will find config files
    [ -e ${S}/config ] || ln -s ${S}/aktualizr/config ${S}/config

    # hack so native build will find sota_tools
    [ -e ${B}/src ] || ln -s ${B}/aktualizr/src ${B}/src
}
do_install_append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/aktualizr-lite.service ${D}${systemd_system_unitdir}/
    install -m 0644 ${WORKDIR}/aktualizr-lite.path ${D}${systemd_system_unitdir}/
}

# Force same RDEPENDS, packageconfig rdepends common to both
RDEPENDS_${PN}-lite = "${RDEPENDS_aktualizr}"

# These are wrong in meta-updater. Hacking here for now:
FILES_${PN}-lib = " \
                ${libdir}/libaktualizr.so \
                "
FILES_${PN}-secondary-lib = " \
                ${libdir}/libaktualizr_secondary.so \
                "
