package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import external.TicketMasterClient;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		// Term can be empty or null.
		String term = request.getParameter("term");

		//TicketMasterClient client = new TicketMasterClient();
		//RpcHelper.writeJsonArray(response, client.search(lat, lon, null));
		DBConnection connection = DBConnectionFactory.getConnection();
		try {
			List<Item> items = connection.searchItems(lat, lon, term);

			JSONArray array = new JSONArray();
			for (Item item : items) {
				array.put(item.toJSONObject());
			}
			RpcHelper.writeJsonArray(response, array);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

	}
}

/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
 *      response)
 */
