import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export default function Home() {
  const [owners, setOwners] = useState([]);

  const { id } = useParams();

  useEffect(() => {
    loadOwners();
  }, []);

  const loadOwners = async () => {
    const result = await axios.get("http://localhost:8080/api/owner");
    setOwners(result.data);
  };

  const deleteOwner = async (id) => {
    await axios.delete(`http://localhost:8080/api/owner/${id}/delete`);
    loadOwners();
  };

  return (
    <div className="container">
      <div className="py-4">
        <Link className="btn btn-success" to="/addowner">
          Add Owner
        </Link>
        <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Id</th>
              <th scope="col">Name</th>
              <th scope="col">Surname</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {owners.map((owner, index) => (
              <tr>
                <th scope="row" key={index}>
                  {index + 1}
                </th>
                <td>{owner.id}</td>
                <td>{owner.name}</td>
                <td>{owner.surname}</td>
                <td>
                  <Link
                    className="btn btn-primary mx-2"
                    to={`/viewowner/${owner.id}`}
                  >
                    View
                  </Link>
                  <Link
                    className="btn btn-outline-primary mx-2"
                    to={`/editowner/${owner.id}`}
                  >
                    Edit
                  </Link>
                  <button
                    className="btn btn-danger mx-2"
                    onClick={() => deleteOwner(owner.id)}
                  >
                    Delete
                  </button>
                  <Link
                    className="btn btn-dark mx-2"
                    to={`/addgarage/${owner.id}`}
                  >
                    Add Garage
                  </Link>

                  <Link
                    className="btn btn-info mx-2"
                    to={`/viewgarages/${owner.id}`}
                  >
                    View Garages
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
