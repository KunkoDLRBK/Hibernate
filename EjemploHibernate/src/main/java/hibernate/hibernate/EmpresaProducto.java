package hibernate.hibernate;

public class EmpresaProducto {

	private static SessionFactory sf;
	
	public EmpresaProducto()
	{
		sf = new Configuration().configure().buildSessionFactory();
	}
	
	public void close() 
	{
		sf.close();
	}
	
	//Mostrar por nombre
	public void mostrarProductosNombre(String nombre)
	{
		Session sesion = sf.openSession();
		Transaction trans = null;
		try 
		{
			//Hago la consulta con el nombre
			trans = sesion.beginTransaction();
			List productos = sesion.createQuery("From Productos Where nombre = '" + nombre + "'").list();
			Iterator it = productos.iterator();
			while (it.hasNext()) 
			{
				//Muestro el producto 
				Productos p = (Productos) it.next();
				System.out.println("--------------------------------------");
				System.out.println("Id: " + p.getId());
				System.out.println("Nombre: " + p.getNombre());
				System.out.println("Precio: " + p.getPrecio());
				System.out.println("--------------------------------------");
			}
			trans.commit();
		} 
		catch (HibernateException e) 
		{
			if (trans != null)
			{
				trans.rollback();
			}
			e.printStackTrace();
		} 
		finally 
		{
			sesion.close();
		}
	}
	
	//Mostrar por precio el producto
	public void MostrarProductoPrecio()
	{
		Session sesion = sf.openSession();
		Transaction trans = null;
		try 
		{
			//Hago la consulta por precio Ascendente
			trans = sesion.beginTransaction();
			List productos = sesion.createQuery("From Productos Order By Price Asc").list();
			Iterator it = productos.iterator();
			while (it.hasNext()) 
			{
				//Muestro producto
				Productos p = (Productos) it.next();
				System.out.println("--------------------------------------");
				System.out.println("Id: " + p.getId());
				System.out.println("Nombre: " + p.getNombre());
				System.out.println("Precio: " + p.getPrecio());
				System.out.println("--------------------------------------");
			}
			trans.commit();
		} 
		catch (HibernateException e) 
		{
			if (trans != null)
			{
				trans.rollback();
			}
			e.printStackTrace();
		} 
		finally 
		{
			sesion.close();
		}
	}
	
	//Mostrar por precio de
	public void mostrarPrecioDe(String nombre)
	{
		Session sesion = sf.openSession();
		Transaction trans = null;
		try 
		{
			//Hago la consulta
			trans = sesion.beginTransaction();
			List productos = sesion.createQuery("From Productos Where nombre = '" + nombre + "'").list();
			Iterator it = productos.iterator();
			while (it.hasNext()) 
			{
				//Muestro la consulta
				Productos p = (Productos) it.next();
				System.out.println("--------------------------------------");
				System.out.println("Precio: " + p.getPrecio());
				System.out.println("--------------------------------------");
			}
			trans.commit();
		} 
		catch (HibernateException e) 
		{
			if (trans != null)
			{
				trans.rollback();
			}
			e.printStackTrace();
		} 
		finally 
		{
			sesion.close();
		}
	}
	
	//Busco el producto
	public void mostrarProductoID(int id)
	{
		Session sesion = sf.openSession();
		Transaction trans = null;
		try 
		{
			//Hago consulta con el ID
			trans = sesion.beginTransaction();
			Query query = sesion.createQuery("From Productos Where id = " + id);
			Productos product = (Productos)query.uniqueResult();
			if (product!=null) 
			{
				//Muestro la consulta
				System.out.println("--------------------------------------");
				System.out.println("Precio: " + product.getPrecio());
				System.out.println("--------------------------------------");
			}
			trans.commit();
		} 
		catch (HibernateException e) 
		{
			if (trans != null)
			{
				trans.rollback();
			}
			e.printStackTrace();
		} 
		finally 
		{
			sesion.close();
		}
	}
}
