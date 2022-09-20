# Bug Tracking System
This is a support ticket system. Application end users can submit bug reports to the development team. Developers can respond, open, assign responsibility to other developers, and close tickets.

# Initialize the Database
If not using Docker:
```
mysql < schemas/dts.sql
```

If using Docker:
```sh
docker exec -it bts-mysql sh -c 'mysql < /schemas/dts.sql'
```

# Default Login
Username: admin
Password: (empty string)
