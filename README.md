
# t2-s1 Gestión de socios COPAL

## Variables de entorno

 - DB_URL: Dirección y nombre de la base de datos
 - USERNAME
 - PASSWORD

## API
//TODO Swagger

Socio:

    {
	    "id": Long,
	    "denominacion": String,
	    "telefono": String,
	    "tipo": Enum (Camara, Empresa) 
	    "mail": String,
	    "descripcion": String,
	    "web": String,
	    "fechaAlta": LocalDateTime,
	    "logo": String,
	    "categoria": {
	        "id": Long,
	        "descripcion": String,
	        "prioridad": Integer
		}
    }

|Método  |Ruta  |Json |
|--|--|--|
|GET  |/socios  ||
|GET  |/socios/{id}  ||
|POST  |/socios  |Socio|
|DELETE  |/socios/{id}  ||
|PUT  |/socios/{id}  |Socio|
