@REM ----------------------------------------------------------------------------
@REM AUTHOR:    Alassani ABODJI
@REM DATE:      13/01/2023
@REM REV:       1.1.A (Valid are A for Alpha, B for Beta, D for Development, 
@REM            T for Test and P for Production)
@REM            
@REM PLATFORM:  Microsoft Windows 
@REM 
@REM PUPOSE:    This script is used first, to remove Docker image and its  
@REM            containers, and then rebuild the image and create a container
@REM            named waad-ecommerce-postgres from it.
@REM            
@REM REV LIST:  
@REM    DATE:  
@REM    BY:  
@REM    MODIFICATION:  
@REM     
@REM ----------------------------------------------------------------------------

@REM ----------------------------------------------------------------------------
@REM                DECLARING VARIABLES
@REM ----------------------------------------------------------------------------
@echo off

@REM Create a variable that contains the directory for PostgreSQL data from the host side
set DB_DATA_DIR=C:/waad-apps/docker/db/waad-ecommerce

@REM Create a variable that contains the directory for PostgreSQL data from the guest 
@REM side(Container). This should map to the corresponding directory from the host side.
set CONTAINER_DB_DATA_DIR=/var/lib/postgresql/data

@REM Create a variable that contains the name of the Docker container to build
set CONTAINER_NAME=waad-ecommerce-postgres

@REM Create a variable that contains the name of the Docker image to build
set IMAGE_NAME=alassani/%CONTAINER_NAME%

@REM Create a variable that contains the path of the Dockerfile
set DOCKERFILE=db/Dockerfile.postgres

@REM Create a variable that contains the port used on the host side
set HOST_PORT=5434

@REM Create a variable that contains the port used on the container side
set CONTAINER_PORT=5432

@REM ----------------------------------------------------------------------------
@REM                EXECUTING DIFFERENT DOCKER COMMANDS
@REM ----------------------------------------------------------------------------

@REM The Mongo DB data must not be deleted after re-running the container, so
@REM the volume for Mongo DB data must be created once. To do so, we need to 
@REM check if the volume doesn't exist before creating it.
@REM 
@REM So let's filter the list of all volumes based on their names, and only
@REM returning their names.
@REM FOR /F %%V IN ('docker volume ls -qf "name=%VOLUME_NAME%"') DO (
@REM     set REMOVEABLE_VOLUME=%%V
@REM 
@REM     IF "%REMOVEABLE_VOLUME%" == "" (
@REM         docker volume create %VOLUME_NAME%
@REM     ) ELSE (
@REM         echo Docker volume with the name %VOLUME_NAME% exists already!
@REM     )
@REM )


@REM The Docker container will only be deleted if it does exist.
@REM So let's filter the list of all containers based on their names, but only
@REM returning their IDs.
FOR /F %%C IN ('docker container ls -aqf "name=%CONTAINER_NAME%"') DO (
    set CONTAINER_ID=%%C

    IF "%CONTAINER_ID%" == "" (
        echo No Docker container with the name %CONTAINER_NAME% found!
    ) ELSE (
        docker container stop %CONTAINER_NAME%
        docker container rm --force %CONTAINER_NAME%
        echo Docker container with the name %CONTAINER_NAME% has been removed!
    )
)

@REM The Docker image will only be deleted if it does exist.
@REM So let's filter the list of all Docker images based on the reference/name,
@REM but only returning their IDs.
FOR /F %%A IN ('docker image ls -qf "reference=%IMAGE_NAME%"') DO (
    set IMAGE_ID=%%A

    IF "%IMAGE_ID%" == "" (
        echo No Docker image with the name %IMAGE_NAME% found!
    ) ELSE (
        docker image rm --force %IMAGE_NAME%
        echo Docker image with the name %IMAGE_NAME% has been removed!
    )
)

@REM Time to build the updated Docker image.
docker build --tag=%IMAGE_NAME% --file=%DOCKERFILE% .

@REM Time to run the container based on the updated Docker image.
docker run -d -it -p %HOST_PORT%:%CONTAINER_PORT% -v %DB_DATA_DIR%:%CONTAINER_DB_DATA_DIR% --name=%CONTAINER_NAME% %IMAGE_NAME%

echo Your container:%CONTAINER_NAME% is now up and running in background