import axios from "axios";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function AccessibleGarages() {
  const [garages, setGarages] = useState([]);

  const { id } = useParams();

  useEffect(() => {
    loadGarages();
  }, []);

  const loadGarages = async () => {
    const result = await axios.get(
      `http://localhost:8080/api/cars/${id}/garages`
    );
    setGarages(result.data);
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
                <td></td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
