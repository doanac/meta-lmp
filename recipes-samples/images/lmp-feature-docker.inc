# Docker packages
CORE_IMAGE_BASE_INSTALL += " \
    docker-ce \
    docker-app \
    docker-credential-helper-fio \
    docker-swarm \
"

EXTRA_USERS_PARAMS += "\
usermod -a -G docker ${LMP_USER}; \
"
