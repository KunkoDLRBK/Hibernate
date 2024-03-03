package hibernate.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import com.mysql.cj.Session;
import com.mysql.cj.xdevapi.SessionFactory;
import org.hibernate.HibernateException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
public class ClientesClase {

	private static SessionFactory sf;
	
	//Condiguro la sesison siempre
	 public ClientesClase()
		{
			sf = new Configuration().configure().buildSessionFactory();
		}
	 
	 //Cierro la sesion
	 public void close() 
		{
			sf.close();
		}
	 
	 //Añado clientes
	 public void AnadirClientes(String Nombre, String Pais) {
		 //Abrimos sesion 
		Session sesion = sf.openSession();
		Transaction trans = null;
		//Creamos clientes y le pasamos los
		Clientes c = new Clientes(Nombre, Pais);
		try 
		{
			//Mandamos lo que queremos añadir a la tabla, estableciendo una "conexion" y pasandole el objeto.
			trans = sesion.beginTransaction();
			sesion.save(c);
			trans.commit();
			System.out.println("Cliente Añadido.");
		} 
		catch (Exception e) 
		{
			if (trans != null) 
			{
				//Si hay error, que no pase nada en la BDD
				trans.rollback();
			}
		} 
		finally 
		{
			//Pase lo que pase cerramos sesion.
			sesion.close();
		}
	}

	 //Muestro los clientes
	 public void MostrarClientes() 
	 {
		//Abrimos sesion 
		Session sesion = sf.openSession();
		Transaction trans = null;
		try 
		{
			//Establecemos conexion para recibir todos los clientes y mostrarlos.
			trans = sesion.beginTransaction();
			List clientes = sesion.createQuery("From Clientes").list();
			Iterator it = clientes.iterator();
			while (it.hasNext()) 
			{
				//Muestro cada cliente que hay en la tabla
				Clientes c = (Clientes) it.next();
				System.out.println("El ID es: "+c.getId()+", el nombre es "+c.getNombre()+", y es del pais "+c.getPais());
			}
			trans.commit();
		} 
		catch (HibernateException e) 
		{
			if (trans != null)
			{
				//Si hay error, que no pase nada en la BDD
				trans.rollback();
			}
		} 
		finally 
		{
			//Pase lo que pase cerramos sesion.
			sesion.close();
		}
	}
	 
	 //Eliminar Cliente
	public void EliminarClienteID(int id) 
	{
		
		Clientes c = new Clientes();
		Session sesion = sf.openSession();
		Transaction trans = null;
		try 
		{
			//Comprobamos que exista el cliente y lo madanmos a borrar o no.
			trans = sesion.beginTransaction();
			c = (Clientes) sesion.get(Clientes.class, id);
			if (c != null) 
			{
				sesion.delete(c);
				trans.commit();
				System.out.println("Se ha borrado correctamente");
			} 
			else 
			{
				System.out.println("No se ha podido borrar el cliente con la ID");
			}
		}catch (HibernateException e) 
		{
			if (trans != null)
			{
				//Si hay error, que no pase nada en la BDD
				trans.rollback();
			}
		} 
		finally 
		{
			//Pase lo que pase cerramos sesion.
			sesion.close();
		}
	}
	
	//Actualizo el cliente
	public void actualizarClienteID(int id, String Nombre, String Pais) 
	{
		
		Clientes c = new Clientes();
		Session sesion = sf.openSession();
		Transaction trans = null;
		try 
		{
			//Comprobamos que el nombre y pais este rellenos y no vacios.
			trans = sesion.beginTransaction();
			c = (Clientes) sesion.load(Clientes.class, id);
			if (!Nombre.equals(""))
			{
				c.setNombre(Nombre);				
			}
			if (!Pais.equals(""))
			{
				c.setPais(Pais);
			}
			//Actulizamos los dtos mandandolos mediante el objeto cliente 
			sesion.update(c);
			trans.commit();
			System.out.println("Se han actulizado los datos del cliente");
		} 
		catch (HibernateException e) 
		{
			if (trans != null)
			{
				//Si hay error, que no pase nada en la BDD
				trans.rollback();
			}
		} 
		finally 
		{
			//Pase lo que pase cerramos sesion.
			sesion.close();
		}
	}
	
	//Borrar cliente
	public void EliminarClienteNombre(String nombre)
	{
		Session sesion = sf.openSession();
		Transaction trans = null;
		try 
		{
			//Le paso el nombre para eliminarlo
			trans = sesion.beginTransaction();
			List clientes = sesion.createQuery("From Clientes Where nombre = '" + nombre + "'").list();
			Iterator it = clientes.iterator();
			while (it.hasNext()) 
			{
				//Mando el cliente a eliminar por el nombre
				Clientes c = (Clientes) it.next();
				System.out.println("Cliente eliminado");
				sesion.delete(c);
			}
			trans.commit();
		} catch (Exception e) {
			if (trans != null) {
				trans.rollback();
			}
		} finally {
			sesion.close();
		}
	}
	
	//Mostrar cliente por pais
	public void MostrarClientesPais(String pais) 
	{
		Session sesion = sf.openSession();
		Transaction trans = null;
		try 
		{
			//Mando el pais para que me saque los clientes de ese pais
			trans = sesion.beginTransaction();
			List clientes = sesion.createQuery("From Clientes Where pais = '" + pais + "'").list();
			Iterator it = clientes.iterator();
			while (it.hasNext()) 
			{
				//Muestro el cliente
				Clientes c = (Clientes) it.next();
				System.out.println("El cliente con id: "+c.getId()+", Su nombre es "+c.getNombre()+" su pais es "+c.getPais());
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

