First, unzip the file
run the docker compose build command
run the docker compose up for start and docker compose down to stop
Hit an api: localhost:8080/auth/login to access token
copy the token and put it as bearer token of job api
Which is localhost:8080/job/load-students to start a batch job
Hit an api: localhost:8080/student/all to see the record.
