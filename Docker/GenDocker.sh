#!/bin/bash

cp ../RentaMonedaExtranjera/target/RentaMonedaExtranjera-2.0.0.0.ear .

docker build -t renta-moneda-extranjera:T.2.0.0.0 .

docker tag renta-moneda-extranjera:T.2.0.0.0  604753321405.dkr.ecr.us-east-1.amazonaws.com/monex-renta-moneda-extranjera:T.2.0.0.0

#docker push 604753321405.dkr.ecr.us-east-1.amazonaws.com/RentaMonedaExtranjera:T.2.0.0.0

