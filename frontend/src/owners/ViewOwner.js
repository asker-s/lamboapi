import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export default function ViewOwner() {
  const [owner, setOwner] = useState({
    name: "",
    surname: "",
  });

  const { id } = useParams();

  useEffect(() => {
    loadOwner();
  }, []);

  const loadOwner = async () => {
    const result = await axios.get(`http://localhost:8080/api/owner/${id}`);
    setOwner(result.data);
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Owner Details</h2>
          <div className="card">
            <div className="card-header">
              Details of owner id :{owner.id}
              <ul className="list-group list-group-flush">
                <li className="list-group-item">
                  <b>Name:</b>
                  {owner.name}
                </li>
                <li className="list-group-item">
                  <b>Surname:</b>
                  {owner.surname}
                </li>
              </ul>
            </div>
          </div>
          <Link className="btn btn-primary my-2" to={"/"}>
            Back to Owners
          </Link>
        </div>
      </div>
    </div>
  );
}
