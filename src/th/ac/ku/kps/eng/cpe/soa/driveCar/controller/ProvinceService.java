package th.ac.ku.kps.eng.cpe.soa.driveCar.controller;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.Produces;
import th.ac.ku.kps.eng.cpe.soa.driveCar.dao.province.ProvinceDAO;
import th.ac.ku.kps.eng.cpe.soa.driveCar.model.Province;
import th.ac.ku.kps.eng.cpe.soa.driveCar.response.model.ResponseList;

@DeclareRoles({"ADMIN","CUSTOMER","GUEST"})
@Path("/services")
public class ProvinceService {
	ProvinceDAO provinceDAO = new ProvinceDAO();

	@GET
	@PermitAll
	@Path("/provinces")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProvince() {
		try {
		ResponseList<Province> res = new ResponseList<Province>();
		res.setMsg("success");
		res.setStatus("200");
		res.setResult(provinceDAO.findAll("name",true));
		return Response.status(201).entity(res).build();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
}
