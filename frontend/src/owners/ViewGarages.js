import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export default function ViewGarages() {
  const [garages, setGarages] = useState([]);

  const { id } = useParams();

  useEffect(() => {
    loadGarages();
  }, []);

  const loadGarages = async () => {
    const result = await axios.get(
      `http://localhost:8080/api/owner/${id}/garages`
    );
    setGarages(result.data);
  };

  const deleteGarage = async (id, garageId) => {
    await axios.delete(
      `http://localhost:8080/api/owner/${id}/garages/${garageId}`
    );
    loadGarages();
  };

  return (
    <div className="container">
      <div className="py-4">
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Id</th>
              <th scope="col">Name</th>
              <th scope="col">Location</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {garages.map((garage, index) => (
              <tr>
                <th scope="row" key={index}>
                  {index + 1}
                </th>
                <td>{garage.id}</td>
                <td>{garage.name}</td>
                <td>{garage.location}</td>
                <td>
                  <Link
                    className="btn btn-primary mx-2"
                    to={`/viewgarage/${id}/${garage.id}`}
                  >
                    View
                  </Link>
                  <button
                    className="btn btn-danger mx-2"
                    onClick={() => deleteGarage(id, garage.id)}
                  >
                    Delete
                  </button>
                  <Link
                    className="btn btn-dark mx-2"
                    to={`/viewgarages/${id}/${garage.id}/cars`}
                  >
                    View Cars
                  </Link>
                  <Link
                    className="btn btn-info mx-2"
                    to={`/owner/${id}/garages/${garage.id}/cars/assign`}
                  >
                    Assign Car
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
