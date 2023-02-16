import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function ViewCars() {
  const [cars, setCars] = useState([]);

  const { ownerId, garageId } = useParams();

  useEffect(() => {
    loadCars();
  }, []);

  const loadCars = async () => {
    const result = await axios.get(
      `http://localhost:8080/api/owner/${ownerId}/garages/${garageId}/cars`
    );
    setCars(result.data);
  };

  const deleteCar = async (ownerId, garageId, carId) => {
    await axios.delete(
      `http://localhost:8080/api/owner/${ownerId}/garages/${garageId}/cars/${carId}`
    );
    loadCars();
  };

  return (
    <div className="container">
      <div className="py-4">
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Id</th>
              <th scope="col">Brand</th>
              <th scope="col">Model</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {cars.map((car, index) => (
              <tr>
                <th scope="row" key={index}>
                  {index + 1}
                </th>
                <td>{car.id}</td>
                <td>{car.brand}</td>
                <td>{car.model}</td>
                <td>
                  <button
                    className="btn btn-danger mx-2"
                    onClick={() => deleteCar(ownerId, garageId, car.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
