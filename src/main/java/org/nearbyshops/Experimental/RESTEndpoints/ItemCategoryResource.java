//package org.nearbyshops.RESTEndpoints;
//
//import net.coobird.thumbnailator.Thumbnails;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import org.nearbyshops.Constants;
//import org.nearbyshops.DAOs.DAORoles.DAOStaff;
//import org.nearbyshops.DAOs.ItemCategoryDAO;
//import org.nearbyshops.Model.Image;
//import org.nearbyshops.Model.ItemCategory;
//import org.nearbyshops.Model.ModelEndpoint.ItemCategoryEndPoint;
//import org.nearbyshops.Model.ModelRoles.StaffPermissions;
//import org.nearbyshops.Model.ModelRoles.User;
//import org.nearbyshops.Utility.UserAuthentication;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.annotation.security.RolesAllowed;
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.Status;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URI;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//
//
//
//
//@Path("/api/v1/ItemCategory")
//public class ItemCategoryResource {
//
//
//
//
//	Logger logger = LoggerFactory.getLogger(ItemCategoryREST.class);
//
//	@Autowired
//	ItemCategoryDAO itemCategoryDAO;
//
//	@Autowired
//	DAOStaff daoStaff;
//
//
//
//	@Autowired
//	private HttpServletRequest request;
//
//	@Autowired
//	private UserAuthentication userAuthentication;
//
//
//
//
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	@RolesAllowed({Constants.ROLE_ADMIN, Constants.ROLE_STAFF})
//	public Response saveItemCategory(ItemCategory itemCategory)
//	{
//
//
//		User user = userAuthentication.isUserAllowed(request, Arrays.asList(Constants.ROLE_STAFF));
//
//		if(user==null)
//		{
//			return Response.status(Status.FORBIDDEN)
//					.build();
//		}
//
//
//
//		if(user.getRole()== Constants.ROLE_STAFF_CODE) {
//
//			StaffPermissions permissions = daoStaff.getStaffPermissions(user.getUserID());
//
//			if (!permissions.isPermitCreateUpdateItemCat())
//			{
//				// the staff member doesnt have persmission to post Item Category
//				throw new ForbiddenException("Not Permitted");
//			}
//		}
//
//
//
//
//
//		int idOfInsertedRow = itemCategoryDAO.saveItemCategory(itemCategory,false);
//		itemCategory.setItemCategoryID(idOfInsertedRow);
//
//
//
//		if(idOfInsertedRow >=1)
//		{
//
//			return Response.status(Status.CREATED)
//					.entity(itemCategory)
//					.build();
//
//		}
//		else {
//
//			return Response.status(Status.NOT_MODIFIED)
//					.build();
//		}
//
//
//	}
//
//
//
//
//	@DELETE
//	@Path("/{ItemCategoryID}")
//	@Produces(MediaType.APPLICATION_JSON)
//	@RolesAllowed({Constants.ROLE_ADMIN, Constants.ROLE_STAFF})
//	public Response deleteItemCategory(@PathParam("ItemCategoryID")int itemCategoryID)
//	{
//
//
//		User user = userAuthentication.isUserAllowed(request, Arrays.asList(Constants.ROLE_STAFF));
//
//		if(user==null)
//		{
//			return Response.status(Status.FORBIDDEN)
//					.build();
//		}
//
//
//
//
//		if(user.getRole()== Constants.ROLE_STAFF_CODE) {
//
//			StaffPermissions permissions = daoStaff.getStaffPermissions(user.getUserID());
//
//			if (!permissions.isPermitCreateUpdateItemCat())
//			{
//				// the staff member doesnt have persmission to post Item Category
//
//				throw new ForbiddenException("Not Permitted");
//			}
//		}
//
//
//
//
//		ItemCategory itemCategory = itemCategoryDAO.getItemCatImageURL(itemCategoryID);
//		int rowCount = itemCategoryDAO.deleteItemCategory(itemCategoryID);
//
//
//		if(itemCategory!=null && rowCount>=1)
//		{
//
//			try {
//
//
//				if(itemCategory.getImagePath()!=null)
//				{
//					deleteImageFileInternal(itemCategory.getImagePath());
//				}
//
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//
//
//
//
//		if(rowCount>=1)
//		{
//			return Response.status(Status.OK)
//					.build();
//		}
//		else if(rowCount == 0)
//		{
//			return Response.status(Status.NOT_MODIFIED)
//					.build();
//		}
//
//		return null;
//	}
//
//
//
//
//
//	@PUT
//	@Path("/ChangeParent/{ItemCategoryID}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@RolesAllowed({Constants.ROLE_ADMIN, Constants.ROLE_STAFF})
//	public Response changeParent(@PathParam("ItemCategoryID")int itemCategoryID, ItemCategory itemCategory)
//	{
//
//
//		User user = userAuthentication.isUserAllowed(request, Arrays.asList(Constants.ROLE_STAFF));
//
//		if(user==null)
//		{
//			return Response.status(Status.FORBIDDEN)
//					.build();
//		}
//
//
//
//		if(user.getRole()== Constants.ROLE_STAFF_CODE) {
//
//			StaffPermissions permissions = daoStaff.getStaffPermissions(user.getUserID());
//
//			if (!permissions.isPermitCreateUpdateItemCat())
//			{
//				// the staff member doesnt have persmission to post Item Category
//
//				throw new ForbiddenException("Not Permitted");
//			}
//		}
//
//
//
//
//		itemCategory.setItemCategoryID(itemCategoryID);
//		int rowCount = itemCategoryDAO.changeParent(itemCategory);
//
//
//
//		if(rowCount >= 1)
//		{
//
//			return Response.status(Status.OK)
//					.build();
//		}
//		else if(rowCount == 0)
//		{
//
//			return Response.status(Status.NOT_MODIFIED)
//					.build();
//		}
//
//		return null;
//	}
//
//
//
//	@PUT
//	@Path("/ChangeParent")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@RolesAllowed({Constants.ROLE_ADMIN, Constants.ROLE_STAFF})
//	public Response changeParentBulk(List<ItemCategory> itemCategoryList)
//	{
//
//		User user = userAuthentication.isUserAllowed(request, Arrays.asList(Constants.ROLE_STAFF));
//
//		if(user==null)
//		{
//			return Response.status(Status.FORBIDDEN)
//					.build();
//		}
//
//
//		if(user.getRole()== Constants.ROLE_STAFF_CODE) {
//
//			StaffPermissions permissions = daoStaff.getStaffPermissions(user.getUserID());
//
//			if (!permissions.isPermitCreateUpdateItemCat())
//			{
//				// the staff member doesnt have persmission to post Item Category
//
//				throw new ForbiddenException("Not Permitted");
//			}
//		}
//
//
//		int rowCountSum = 0;
//
//		rowCountSum = itemCategoryDAO.changeParentBulk(itemCategoryList);
//
//
//
//		if(rowCountSum ==  itemCategoryList.size())
//		{
//
//			return Response.status(Status.OK)
//					.build();
//		}
//		else if( rowCountSum < itemCategoryList.size() && rowCountSum > 0)
//		{
//
//			return Response.status(Status.PARTIAL_CONTENT)
//					.build();
//		}
//		else if(rowCountSum == 0 ) {
//
//			return Response.status(Status.NOT_MODIFIED)
//					.build();
//		}
//
//		return null;
//	}
//
//
//
//
//	@PUT
//	@Path("/{ItemCategoryID}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@RolesAllowed({Constants.ROLE_ADMIN, Constants.ROLE_STAFF})
//	public Response updateItemCategory(@PathParam("ItemCategoryID")int itemCategoryID, ItemCategory itemCategory)
//	{
//
//
//
//
//		User user = userAuthentication.isUserAllowed(request, Arrays.asList(Constants.ROLE_STAFF));
//
//		if(user==null)
//		{
//			return Response.status(Status.FORBIDDEN)
//					.build();
//		}
//
//
//
//		if(user.getRole()== Constants.ROLE_STAFF_CODE) {
//
//			StaffPermissions permissions = daoStaff.getStaffPermissions(user.getUserID());
//
//			if (!permissions.isPermitCreateUpdateItemCat())
//			{
//				// the staff member doesnt have persmission to post Item Category
//
//				throw new ForbiddenException("Not Permitted");
//			}
//		}
//
//
//
//		itemCategory.setItemCategoryID(itemCategoryID);
//
//
//		int rowCount = itemCategoryDAO.updateItemCategory(itemCategory);
//
//		if(rowCount >= 1)
//		{
//
//			return Response.status(Status.OK)
//					.build();
//		}
//		else if(rowCount == 0)
//		{
//
//			return Response.status(Status.NOT_MODIFIED)
//					.build();
//		}
//
//		return null;
//	}
//
//
//
//
//
//
//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	@RolesAllowed({Constants.ROLE_ADMIN, Constants.ROLE_STAFF})
//	public Response updateItemCategoryBulk(List<ItemCategory> itemCategoryList)
//	{
//
//
//
//		User user = userAuthentication.isUserAllowed(request, Arrays.asList(Constants.ROLE_STAFF));
//
//		if(user==null)
//		{
//			return Response.status(Status.FORBIDDEN)
//					.build();
//		}
//
//
//
//		if(user.getRole()== Constants.ROLE_STAFF_CODE) {
//
//			StaffPermissions permissions = daoStaff.getStaffPermissions(user.getUserID());
//
//			if (!permissions.isPermitCreateUpdateItemCat())
//			{
//				// the staff member doesnt have persmission to post Item Category
//
//				throw new ForbiddenException("Not Permitted");
//			}
//		}
//
//
//		int rowCountSum = 0;
//
//		for(ItemCategory itemCategory : itemCategoryList)
//		{
//			rowCountSum = rowCountSum + itemCategoryDAO.updateItemCategory(itemCategory);
//		}
//
//		if(rowCountSum ==  itemCategoryList.size())
//		{
//
//			return Response.status(Status.OK)
//					.build();
//		}
//		else if( rowCountSum < itemCategoryList.size() && rowCountSum > 0)
//		{
//
//			return Response.status(Status.PARTIAL_CONTENT)
//					.build();
//		}
//		else if(rowCountSum == 0 ) {
//
//			return Response.status(Status.NOT_MODIFIED)
//					.build();
//		}
//
//		return null;
//	}
//
//
//
//
//
//
//
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/QuerySimple")
//	public Response getItemCategoriesQuerySimple(
//            @QueryParam("ParentID")Integer parentID,
//            @QueryParam("IsDetached")Boolean parentIsNull,
//            @QueryParam("SearchString") String searchString,
//            @QueryParam("SortBy") String sortBy,
//            @QueryParam("Limit") Integer limit, @QueryParam("Offset")int offset,
//            @QueryParam("metadata_only")Boolean metaonly
//	)
//	{
//
//		final int max_limit = 100;
//
//		if(limit!=null)
//		{
//			if(limit>=max_limit)
//			{
//				limit = max_limit;
//			}
//
//		}
//
//
//
//
//		ItemCategoryEndPoint endPoint = itemCategoryDAO
//				.getItemCategoriesSimplePrepared(
//						parentID,
//						parentIsNull,
//						searchString,
//						sortBy,limit,offset);
//
//
//		endPoint.setLimit(limit);
//		endPoint.setMax_limit(max_limit);
//		endPoint.setOffset(offset);
//
//
//
//		//Marker
//		return Response.status(Status.OK)
//				.entity(endPoint)
//				.build();
//	}
//
//
//
//
//
//
//
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getItemCategories(
//            @QueryParam("ShopID")Integer shopID,
//            @QueryParam("ParentID")Integer parentID, @QueryParam("IsDetached")Boolean parentIsNull,
//            @QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
//            @QueryParam("deliveryRangeMax")Double deliveryRangeMax,
//            @QueryParam("deliveryRangeMin")Double deliveryRangeMin,
//            @QueryParam("proximity")Double proximity,
//            @QueryParam("ShopEnabled")Boolean shopEnabled,
//            @QueryParam("SearchString") String searchString,
//            @QueryParam("SortBy") String sortBy,
//            @QueryParam("Limit") Integer limit, @QueryParam("Offset") int offset,
//            @QueryParam("metadata_only")Boolean metaonly)
//	{
//
//
//
//		final int max_limit = 100;
//
//		if(limit!=null)
//		{
//			if(limit>=max_limit)
//			{
//				limit = max_limit;
//			}
//		}
//
//
//
//		ItemCategoryEndPoint endPoint = new ItemCategoryEndPoint();
//
//
//		ArrayList<ItemCategory> list = null;
//
//
//
//		if(metaonly==null || (!metaonly)) {
//			list = itemCategoryDAO.getItemCategoriesJoinRecursive(
//					shopID, parentID, parentIsNull,
//					latCenter, lonCenter,
//					shopEnabled,
//					searchString,
//					sortBy,
//					limit, offset);
//
//			endPoint.setResults(list);
//		}
//
//
//
//
//		endPoint.setLimit(limit);
//		endPoint.setMax_limit(max_limit);
//		endPoint.setOffset(offset);
//
//
//
//		return Response.status(Status.OK)
//                .entity(endPoint)
//                .build();
//
//	}
//
//
//
//
//
//
//	@GET
//	@Path("/GetItemCategoryDetails")
//	@Produces(MediaType.APPLICATION_JSON)
//	@RolesAllowed({Constants.ROLE_END_USER})
//	public Response getItemCategoryDetails(@QueryParam("ItemCategoryID")Integer itemCategoryID)
//	{
//		ItemCategory itemCategory = itemCategoryDAO.getItemCategoryDetails(itemCategoryID);
//
//		//Marker
//		return Response.status(Status.OK)
//				.entity(itemCategory)
//				.build();
//	}
//
//
//
//
//
//
//	// Image Utility Methods
//
//	private void deleteImageFileInternal(String fileName)
//	{
//		boolean deleteStatus = false;
//
////		System.out.println("Filename: " + fileName);
//
//		try {
//
//			//Files.delete(BASE_DIR.resolve(fileName));
//			deleteStatus = Files.deleteIfExists(BASE_DIR.resolve(fileName));
//
//			// delete thumbnails
//			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + fileName + ".jpg"));
//			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + fileName + ".jpg"));
//
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//	}
//
//
//
//
//	private String saveNewImage(String serviceURL,String imageID)
//	{
//		try
//		{
//			serviceURL = serviceURL + "/api/v1/ItemCategory/Image/" + imageID;
//
//			OkHttpClient client = new OkHttpClient();
//			Request request = new Request.Builder()
//					.url(serviceURL)
//					.build();
//
//			okhttp3.Response response = null;
//			response = client.newCall(request).execute();
//			response.body().byteStream();
////			System.out.println();
//
//			return uploadNewImage(response.body().byteStream());
//
//		}
//		catch (Exception ex)
//		{
//			ex.printStackTrace();
//		}
//
//		return null;
//	}
//
//
//
//
//	private String uploadNewImage(InputStream in)
//	{
//
//		File theDir = new File(BASE_DIR.toString());
//
//		// if the directory does not exist, create it
//		if (!theDir.exists()) {
//
////			System.out.println("Creating directory: " + BASE_DIR.toString());
//
//			boolean result = false;
//
//			try{
//				theDir.mkdir();
//				result = true;
//			}
//			catch(Exception se){
//				//handle it
//			}
//			if(result) {
////				System.out.println("DIR created");
//			}
//		}
//
//
//
//		String fileName = "" + System.currentTimeMillis();
//
//
//		try {
//
//			// Copy the file to its location.
//			long filesize = 0;
//
//			filesize = Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
//
//			if(filesize > MAX_IMAGE_SIZE_MB * 1048 * 1024)
//			{
//				// delete file if it exceeds the file size limit
//				Files.deleteIfExists(BASE_DIR.resolve(fileName));
//				return null;
//			}
//
//			createThumbnails(fileName);
//
//			Image image = new Image();
//			image.setPath(fileName);
//
//			// Return a 201 Created response with the appropriate Location header.
//
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//
//			return null;
//		}
//
//		return fileName;
//	}
//
//
//
//
//
//
//
//	// Image MEthods
//
//	private static final java.nio.file.Path BASE_DIR = Paths.get("./data/images/ItemCategory");
//	private static final double MAX_IMAGE_SIZE_MB = 10;
//
//
//	@POST
//	@Path("/Image")
//	@Consumes({MediaType.APPLICATION_OCTET_STREAM})
//	@RolesAllowed({Constants.ROLE_ADMIN, Constants.ROLE_STAFF})
//	public Response uploadImage(InputStream in, @HeaderParam("Content-Length") long fileSize,
//                                @QueryParam("PreviousImageName") String previousImageName
//	) throws Exception
//	{
//
//
//
//
//		User user = userAuthentication.isUserAllowed(request, Arrays.asList(Constants.ROLE_STAFF));
//
//		if(user==null)
//		{
//			return Response.status(Status.FORBIDDEN)
//					.build();
//		}
//
//
//
//		if(user.getRole()== Constants.ROLE_STAFF_CODE) {
//
//			StaffPermissions permissions = daoStaff.getStaffPermissions(user.getUserID());
//
//			if (!permissions.isPermitCreateUpdateItemCat())
//			{
//				// the staff member doesnt have persmission to post Item Category
//
//				throw new ForbiddenException("Not Permitted");
//			}
//		}
//
//
//		if(previousImageName!=null)
//		{
//			Files.deleteIfExists(BASE_DIR.resolve(previousImageName));
//			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + previousImageName + ".jpg"));
//			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + previousImageName + ".jpg"));
//		}
//
//
//		File theDir = new File(BASE_DIR.toString());
//
//		// if the directory does not exist, create it
//		if (!theDir.exists()) {
//
////			System.out.println("Creating directory: " + BASE_DIR.toString());
//
//			boolean result = false;
//
//			try{
//				theDir.mkdir();
//				result = true;
//			}
//			catch(Exception se){
//				//handle it
//			}
//			if(result) {
////				System.out.println("DIR created");
//			}
//		}
//
//
//
//		String fileName = "" + System.currentTimeMillis();
//
//		// Copy the file to its location.
//		long filesize = Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
//
//		if(filesize > MAX_IMAGE_SIZE_MB * 1048 * 1024)
//		{
//			// delete file if it exceeds the file size limit
//			Files.deleteIfExists(BASE_DIR.resolve(fileName));
//
//			return Response.status(Status.EXPECTATION_FAILED).build();
//		}
//
//
//		createThumbnails(fileName);
//
//
//		Image image = new Image();
//		image.setPath(fileName);
//
//		// Return a 201 Created response with the appropriate Location header.
//
//		return Response.status(Status.CREATED).location(URI.create("/api/Images/" + fileName)).entity(image).build();
//	}
//
//
//
//	private void createThumbnails(String filename)
//	{
//		try {
//
//			Thumbnails.of(BASE_DIR.toString() + "/" + filename)
//					.size(300,300)
//					.outputFormat("jpg")
//					.toFile(new File(BASE_DIR.toString() + "/" + "three_hundred_" + filename));
//
//			//.toFile(new File("five-" + filename + ".jpg"));
//
//			//.toFiles(Rename.PREFIX_DOT_THUMBNAIL);
//
//
//			Thumbnails.of(BASE_DIR.toString() + "/" + filename)
//					.size(500,500)
//					.outputFormat("jpg")
//					.toFile(new File(BASE_DIR.toString() + "/" + "five_hundred_" + filename));
//
//
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//
//
//
//	@GET
//	@Path("/Image/{name}")
//	@Produces("image/jpeg")
//	public InputStream getImage(@PathParam("name") String fileName) {
//
//		//fileName += ".jpg";
//		java.nio.file.Path dest = BASE_DIR.resolve(fileName);
//
//		if (!Files.exists(dest)) {
//			throw new WebApplicationException(Status.NOT_FOUND);
//		}
//
//
//		try {
//
//			return Files.newInputStream(dest);
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//
//
//	@DELETE
//	@Path("/Image/{name}")
//	@RolesAllowed({Constants.ROLE_ADMIN, Constants.ROLE_STAFF})
//	public Response deleteImageFile(@PathParam("name")String fileName)
//	{
//
//
//		User user = userAuthentication.isUserAllowed(request, Arrays.asList(Constants.ROLE_STAFF));
//
//		if(user==null)
//		{
//			return Response.status(Status.FORBIDDEN)
//					.build();
//		}
//
//
//
//
//		if(user.getRole()== Constants.ROLE_STAFF_CODE) {
//
//			StaffPermissions permissions = daoStaff.getStaffPermissions(user.getUserID());
//
//			if (!permissions.isPermitCreateUpdateItemCat())
//			{
//				// the staff member doesnt have persmission to post Item Category
//
//				throw new ForbiddenException("Not Permitted");
//			}
//		}
//
//
//
//		boolean deleteStatus = false;
//
//		Response response;
//
////		System.out.println("Filename: " + fileName);
//
//		try {
//
//
//			//Files.delete(BASE_DIR.resolve(fileName));
//			deleteStatus = Files.deleteIfExists(BASE_DIR.resolve(fileName));
//
//			// delete thumbnails
//			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + fileName + ".jpg"));
//			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + fileName + ".jpg"));
//
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//		if(!deleteStatus)
//		{
//			response = Response.status(Status.NOT_MODIFIED).build();
//
//		}else
//		{
//			response = Response.status(Status.OK).build();
//		}
//
//		return response;
//	}
//
//
//}