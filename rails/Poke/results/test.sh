#!/bin/bash

mkdir pokes
mkdir attacks
cd pokes
echo "Getting pokes"
for i in {0..721}
do
	wget localhost/pokemon/pokemons/$i.json
done

cd ../attacks
echo "Getting attacks"
for i in {0..721}
do
	wget localhost/pokemon/attacks/$i.json
done