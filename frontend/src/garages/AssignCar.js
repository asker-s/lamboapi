import axios from "axios";
import { useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";

export default function AssignCar() {
  let navigate = useNavigate();

  const [carId, setCarId] = useState();

  const handleChange = (event) => {
    const result = event.target.value.replace(/\D/g, "");

    setCarId(result);
  };

  const { ownerId, garageId } = useParams();

  const onSubmit = async (e) => {
    e.preventDefault();
    await axios.put(
      `http://localhost:8080/api/owner/${ownerId}/garages/${garageId}/cars/${carId}/assign`
    );
    navigate(`/viewgarages/${ownerId}/${garageId}/cars`);
  };

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-6 offset-md-3 border rounded p-4 mt-2 shadow">
          <h2 className="text-center m-4">Assign Car</h2>
          <form onSubmit={(e) => onSubmit(e)}>
            <div className="mb-3">
              <label htmlFor="Car Id" className="form-label">
                Car Id
              </label>
              <input
                type="text"
                className="form-control"
                placeholder="Enter the Car Id"
                value={carId}
                onChange={handleChange}
              />
            </div>
            <button type="submit" className="btn btn-outline-primary">
              Submit
            </button>
            <Link
              className="btn btn-outline-danger mx-2"
              to={`/viewgarages/${ownerId}`}
            >
              Cancel
            </Link>
          </form>
        </div>
      </div>
    </div>
  );
}
