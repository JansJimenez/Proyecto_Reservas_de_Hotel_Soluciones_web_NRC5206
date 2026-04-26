# Hotel Reservas - Backend

API REST para la gestión de reservas hoteleras, desarrollada con Spring Boot.

## Tecnologías utilizadas
- Java 25 + Spring Boot 4.0.5
- Spring Data JPA + Hibernate
- MySQL (base de datos: hotelreservas_bd, puerto 3306)
- Lombok
- Servidor en el puerto 9090

## Arquitectura
El proyecto implementa una arquitectura en tres capas:
- **Controller** — gestiona las peticiones HTTP y delega la lógica al servicio correspondiente
- **Service** — contiene las reglas de negocio y validaciones
- **Repository** — administra el acceso y persistencia de datos en la base de datos

## Entidades del dominio
- `TipoHabitacion` — categoriza las habitaciones (Simple, Doble, Suite)
- `Habitacion` — registra número, precio por noche, capacidad, servicios y disponibilidad
- `Huesped` — almacena los datos del cliente con DNI y correo únicos
- `Reserva` — vincula al huésped con las habitaciones reservadas y calcula el total
- `DetalleReserva` — representa cada habitación dentro de una reserva

## Endpoints disponibles
| Recurso | Ruta base |
|---|---|
| Tipos de habitación | `/api/tipos-habitacion` |
| Habitaciones | `/api/habitaciones` |
| Huéspedes | `/api/huespedes` |
| Reservas | `/api/reservas` |
| Detalle de reserva | `/api/detalles-reserva` |

## Lógica de negocio destacada
Al registrar una reserva, el sistema:
1. Verifica que la fecha de salida sea posterior a la fecha de ingreso
2. Calcula automáticamente el precio unitario y subtotal por habitación
3. Determina el total a pagar sumando todos los subtotales
4. Actualiza la disponibilidad de cada habitación a no disponible

Al eliminar una reserva, la disponibilidad de las habitaciones asociadas es restaurada.