package net.amoc.service;

import net.amoc.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Atiqur Rahman
 * @since Jun 1, 2013
 */
@Service
public class CommonService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;
}
