package com.salesmanager.shop.store.api.v1.shipping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shipping.PackageDetails;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.references.PersistableAddress;
import com.salesmanager.shop.model.references.ReadableAddress;
import com.salesmanager.shop.store.controller.shipping.facade.ShippingFacade;
import com.salesmanager.shop.utils.AuthorizationUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1")
@Api(tags = { "Shipping configuration resource (Shipping Management Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Shipping management resource", description = "Manage shipping configuration") })
public class ShippingConfigurationApi {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShippingConfigurationApi.class);

	@Autowired
	private AuthorizationUtils authorizationUtils;
	
	@Autowired
	private ShippingFacade shippingFacade;
	
	@ApiOperation(httpMethod = "GET", value = "Get shipping origin for a specific merchant store",
		      notes = "", produces = "application/json", response = ReadableAddress.class)
	@RequestMapping(value = { "/private/shipping/origin" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ReadableAddress shippingOrigin(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {


		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

		return shippingFacade.getShippingOrigin(merchantStore);

	}
	
	@RequestMapping(value = { "/private/shipping/origin" }, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void saveShippingOrigin(
			@RequestBody PersistableAddress address,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {


		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

		shippingFacade.saveShippingOrigin(address, merchantStore);

	}

	
	
	//list packaging
	@ApiOperation(httpMethod = "GET", value = "Get list of configured packages types for a specific merchant store",
		      notes = "", produces = "application/json", response = List.class)
	@RequestMapping(value = { "/private/shipping/packages" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<PackageDetails> listPackages(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {


		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

		return shippingFacade.listPackages(merchantStore);


	}
	
	//get packaging
	@ApiOperation(httpMethod = "GET", value = "Get package details",
		      notes = "", produces = "application/json", response = PackageDetails.class)
	@RequestMapping(value = { "/private/shipping/package/{code}" }, method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public PackageDetails getPackage(
			@PathVariable String code,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {


		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

		return shippingFacade.
				getPackage(code, merchantStore);
		


	}
	
	//create packaging
	@ApiOperation(httpMethod = "POST", value = "Create new package specification",
		      notes = "", produces = "application/json", response = Void.class)
	@RequestMapping(value = { "/private/shipping/package" }, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void createPackage(
			@RequestBody PackageDetails details,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {


		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

		shippingFacade.createPackage(details, merchantStore);

	}
	
	//edit packaging
	@ApiOperation(httpMethod = "PUT", value = "Edit package specification",
		      notes = "", produces = "application/json", response = Void.class)
	@RequestMapping(value = { "/private/shipping/package/{code}" }, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void updatePackage(
			@PathVariable String code,
			@RequestBody PackageDetails details,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {


		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

		shippingFacade.updatePackage(code, details, merchantStore);

	}
	
	//delete packaging
	@ApiOperation(httpMethod = "DELETE", value = "Delete a package specification",
		      notes = "", produces = "application/json", response = Void.class)
	@RequestMapping(value = { "/private/shipping/package/{code}" }, method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deletePackage(
			@PathVariable String code,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {


		String user = authorizationUtils.authenticatedUser();
		authorizationUtils.authorizeUser(user, Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
				Constants.GROUP_SHIPPING, Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()), merchantStore);

		shippingFacade.deletePackage(code, merchantStore);

	}

}
