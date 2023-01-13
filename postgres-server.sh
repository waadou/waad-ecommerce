#!/bin/bash
#
# ----------------------------------------------------------------------------
# AUTHOR:    Alassani ABODJI
# DATE:      13/01/2023
# REV:       1.1.A (Valid are A for Alpha, B for Beta, D for Development, 
#            T for Test and P for Production)
#            
# PLATFORM:  Linux 
# 
# PUPOSE:    This script is used first, to remove Docker image and its  
#            containers, and then rebuild the image and create a container
#            named waad-ecommerce-postgres from it.
#            
# REV LIST:  
#    DATE:  
#    BY:  
#    MODIFICATION:  
#     
# ----------------------------------------------------------------------------

# ----------------------------------------------------------------------------
#                DECLARING VARIABLES
# ----------------------------------------------------------------------------


# Create a variable that contains the name of the volume for MongoDb data
VOLUME_NAME=waad-ecommerce-volume

# Create a variable that contains the name of the directory that contains 
# Mongo DB data on the container
MONGO_DATA_DIR=/data/db

# Create a variable that contains the name of the Docker container to build
CONTAINER_NAME=waad-ecommerce-postgres

# Create a variable that contains the name of the Docker image to build
IMAGE_NAME=alassani/$CONTAINER_NAME

# Create a variable that contains the path of the Dockerfile
DOCKERFILE=db/Dockerfile.postgres

# Create a variable that contains the port used on the host side
HOST_PORT=5433

# Create a variable that contains the port used on the container side
CONTAINER_PORT=5432

# ----------------------------------------------------------------------------
#                EXECUTING DIFFERENT DOCKER COMMANDS
# ----------------------------------------------------------------------------

# The Mongo DB data must not be deleted after re-running the container, so
# the volume for Mongo DB data must be created once. To do so, we need to 
# check if the volume doesn't exist before creating it.
# 
# So let's filter the list of all volumes based on their names, and only
# returning their names.
if [ "$(docker volume ls -qf name=$VOLUME_NAME)" ]; then
    echo "Docker volume with the name $VOLUME_NAME already exists!"
else
    docker volume create $VOLUME_NAME
fi


# The Docker container will only be deleted if it does exist.
# So let's filter the list of all containers based on their names, but only
# returning their IDs.
if [ "$(docker container ls -aqf name=$CONTAINER_NAME)" ]; then
    docker container stop $CONTAINER_NAME
    docker container rm --force $CONTAINER_NAME
    echo "Docker container with the name $CONTAINER_NAME has been removed!"
else
    echo "Docker container with the name $CONTAINER_NAME doesn't exist!"
fi

# The Docker image will only be deleted if it does exist.
# So let's filter the list of all Docker images based on the reference/name,
# but only returning their IDs.
if [ "$(docker image ls -qf reference=$IMAGE_NAME)" ]; then
    docker image rm --force $IMAGE_NAME
    echo "Docker image with the name $IMAGE_NAME has been removed!"
else
    echo "Docker image with the name $IMAGE_NAME doesn't exist!"
fi

# Time to build the updated Docker image.
docker build --tag=$IMAGE_NAME --file=$DOCKERFILE .

# Time to run the container based on the updated Docker image.
docker run -d -it -p $HOST_PORT:$CONTAINER_PORT -v $VOLUME_NAME:$MONGO_DATA_DIR --name=$CONTAINER_NAME $IMAGE_NAME

echo Your container:$CONTAINER_NAME is now up and running in background
