# HyperMetro :metro:

## Table of Contents

1. [Description](#description)
2. [Features](#features)
3. [How to Run](#how-to-run)
4. [Example Commands](#example-commands)

## Description

HyperMetro is a Java-based application designed to simulate metro line management and to find the shortest or fastest routes between stations. Utilizing a graph-based algorithmic approach, the software accurately represents metro lines and stations, allowing users to easily manipulate the metro system and compute optimal paths between any two stations.

## Features

- **Metro Line Management**: Effortlessly create, update, and delete metro lines and stations.
- **Shortest Route**: Calculate the shortest route based on the number of stations between start and destination points.
- **Fastest Route**: Identify the fastest route, accounting for transfer times between lines.
- **Dynamic Updates**: Execute real-time changes to the metro system using simple commands.

## How to Run

1. Clone the repository.
2. Navigate to the project directory.
3. Run the `Main` class.

## Example Commands

- `output <Line Name>`: Display details of a specific metro line.
- `append <Line Name> <Station Name>`: Append a new station to a metro line.
- `add-head <Line Name> <Station Name>`: Add a station to the beginning of a metro line.
- `connect <Line1> <Station1> <Line2> <Station2>`: Create a transfer point between two stations on different lines.
- `route <Start Line> <Start Station> <End Line> <End Station>`: Compute the shortest route between two stations.
- `fastest-route <Start Line> <Start Station> <End Line> <End Station>`: Compute the fastest route between two stations.
- `remove <Line Name> <Station Name>`: Remove a station from a metro line.
