/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Likes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Aprendiz
 */
@Local
public interface LikesFacadeLocal {

    void create(Likes likes);

    void edit(Likes likes);

    void remove(Likes likes);

    Likes find(Object id);

    List<Likes> findAll();

    List<Likes> findRange(int[] range);

    int count();
    
}
