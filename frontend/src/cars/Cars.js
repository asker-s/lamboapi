import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export default function Cars() {
  const [cars, setCars] = useState([]);

  const { id } = useParams();

  useEffect(() => {
    loadCars();
  }, []);

  const loadCars = async () => {
    const result = await axios.get("http://localhost:8080/api/cars");
    setCars(result.data);
  };

  const deleteCar = async () => {
    await axios.delete("http://localhost:8080/api/cars/delete");
    loadCars();
  };

  return (
    <div className="container">
      <div className="py-4">
        <Link className="btn btn-success" to="/addcar">
          Add Car
        </Link>
        <button className="btn btn-danger mx-2" onClick={() => deleteCar()}>
          Delete All Cars
        </button>
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
                  <Link className="btn btn-info" to={`/cars/${car.id}/garages`}>
                    Get All Accessible Garages
                  </Link>
                  <Link
                    className="btn btn-primary"
                    to={`/cars/${car.id}/garages/assign`}
                  >
                    Assign to a Garage
                  </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
