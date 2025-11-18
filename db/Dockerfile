FROM postgres:18.1-alpine3.22

# Metadata de la imagen
LABEL maintainer="AX01 - Systems"
LABEL description="PostgreSQL para desarrollo local"
LABEL version="1.0.0"

# Crear directorio para scripts de inicialización
RUN mkdir -p /docker-entrypoint-initdb.d

# Copiar scripts de inicialización (si existen)
COPY init/*.sql /docker-entrypoint-initdb.d/

# Exponer puerto PostgreSQL
EXPOSE 5432

