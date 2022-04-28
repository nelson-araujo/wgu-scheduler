# wgu-scheduler
## Application Summary
Scheduler is an appointment scheduling application. Scheduler provides the ability to manage customer's contact information and schedule appointments for those customers.

## Running Scheduler
To launch Scheduler:
1. Update the MySQL server address. The default server address is "*localhost*".
   - To update the MySQL server address, open the Datasource class and update the SERVER_NAME final to the correct server IP or hostname.
2. Update the MySQL username and password. The default username is "*sqlUser*" and the password is "*Passw0rd!*".
   - To update the username and password, open the Logger class and update the dbUser and dbUserPass variables.
3. Run the wgScheduler Class main method.
4. When the log in screen appears use the default credentials to log in.
   - Username: *test*
   - Password: *test*

## Versions
The current published version of Scheduler is **1.0.1**, it was released on 2022-April-28.

### Version history
| Version | Release Date  | Description                       |
|---------|---------------|-----------------------------------|
| 1.0.1   | 2022-April-28 | Update MySQL connection defaults. |
| 1.0.0   | 2022-April-14 | Initial release of Scheduler.     |

## Report
The additional report I chose to configure was the "Total appointments per office location". This report displays the total appointments per location over all time.

## Development
### Developer(s)
Scheduler was developed by [Nelson Araujo](https://github.com/nelson-araujo). To contact him use the [narauj4@wgu.edu](narauj4@wgu.edu) email address.

### Tools
Scheduler was developed using the following tools:
- IntelliJ IDEA 2022.1 (Ultimate Edition)
- Amazon Corretto 18.0.1
- JavaFX 17.0.2
- MySQL JDBC 8.0.25