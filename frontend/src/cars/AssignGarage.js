import axios from "axios";
import React, { useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

export default function AssignGarage() {
  let navigate = useNavigate();

  const [garageId, setGarageId] = useState();

  const handleChange = (event) => {
    const result = event.target.value.replace(/\D/g, "");

    setGarageId(result);
  };

  const { carId } = useParams();

  const onSubmit = async (e) => {
    e.preventDefault();

    await axios
      .get(`http://localhost:8080/api/car/${carId}/garages/${garageId}`)
      .then((res) => alert("Car already has access to the garage"))
      .catch(function (error) {
        if (error.response.status === 404) {
          console.log("Garage does not exist");
          alert("Garage does not exist");
          return;
        } else if (error.response.status === 409) {
          axios.put(
            `http://localhost:8080/api/car/${carId}/garages/${garageId}`
          );
          navigate("/cars");
        }
      });
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Assign Garage</h2>
          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="Garage Id" className="form-label">
                Garage Id
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter the Garage Id"
                value={garageId}
                onChange={handleChange}
              />
            </div>
            <button type="submit" className="btn btn-outline-primary">
              Submit
            </button>
            <Link className="btn btn-outline-danger mx-2" to={`/cars`}>
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  );
}
