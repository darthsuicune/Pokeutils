#!/bin/bash
for i in {1..721}
do
	wget localhost/pokemon/attacks/$i.json
done