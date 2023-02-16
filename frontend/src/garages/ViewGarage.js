import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export default function ViewGarage() {
  const [garage, setGarage] = useState({
    name: "",
    location: "",
  });

  const { ownerId, garageId } = useParams();

  useEffect(() => {
    loadGarage();
  }, []);

  const loadGarage = async () => {
    const result = await axios.get(
      `http://localhost:8080/api/owner/${ownerId}/garages/${garageId}`
    );
    setGarage(result.data);
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Garage Details</h2>
          <div className="card">
            <div className="card-header">
              Details of garage id :{garage.id}
              <ul className="list-group list-group-flush">
                <li className="list-group-item">
                  <b>Name:</b>
                  {garage.name}
                </li>
                <li className="list-group-item">
                  <b>Location:</b>
                  {garage.location}
                </li>
              </ul>
            </div>
          </div>
          <Link
            className="btn btn-primary my-2"
            to={`http://localhost:3000/viewgarages/${ownerId}`}
          >
            Back to Garages
          </Link>
        </div>
      </div>
    </div>
  );
}
