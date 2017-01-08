using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Script.Serialization;
using System.Web.Services;

namespace NetWebServiceDemo
{
    /// <summary>
    /// Summary description for WebService
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    [System.Web.Script.Services.ScriptService]
    public class WebService : System.Web.Services.WebService
    {

        [WebMethod]
        public string HelloWorld()
        {
            return "Hello World";
        }

        // demo data object and data
        private class Device
        {
            public int Id { get; set; }
            public string Name { get; set; }
            public string Brand { get; set; }
        }


        private List<Device> Devices = new List<Device>();

        private void prepareDevices()
        {
            // this might be loaded from database
            Devices.Add(new Device { Id = 1, Name = "Nexus 5", Brand = "Android" });
            Devices.Add(new Device { Id = 2, Name = "Nexus 5X", Brand = "Android" });
            Devices.Add(new Device { Id = 3, Name = "Nexus 6", Brand = "Android" });
            Devices.Add(new Device { Id = 4, Name = "Pixel", Brand = "Android" });
            Devices.Add(new Device { Id = 5, Name = "Apple 9", Brand = "Apple" });
            Devices.Add(new Device { Id = 6, Name = "Apple 1", Brand = "Apple" });
            Devices.Add(new Device { Id = 7, Name = "Windows Phone 8", Brand = "Microsoft" });
            Devices.Add(new Device { Id = 8, Name = "Windows Phobe 10", Brand = "Microsoft" });
        }

        private List<Device> getDevicesByBrand(string brand)
        {
            prepareDevices();

            if (brand.ToLower().Equals("all"))
            {
                return Devices;
            }
            return Devices.FindAll(d => d.Brand.ToLower().Equals(brand));
        }


        [WebMethod]
        public string LoadDevices(string brand)
        {
            string data = "";

            try
            {
                // load data from database (optional)
                // this demo returns pre-generated data
                List<Device> devices = getDevicesByBrand(brand);
                if (devices != null && devices.Count > 0)
                {
                    JavaScriptSerializer serializer = new JavaScriptSerializer();
                    data = serializer.Serialize(devices);
                    Context.Response.Output.Write(data);
                    Context.Response.End();
                }
            }
            catch (Exception e)
            {
            }

            return String.Empty;
        }
    }
}
