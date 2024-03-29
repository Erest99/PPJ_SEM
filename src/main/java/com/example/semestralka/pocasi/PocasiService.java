package com.example.semestralka.pocasi;

import com.example.semestralka.SemestralkaApplication;
import com.example.semestralka.mesto.Mesto;
import com.example.semestralka.mesto.MestoRepository;
import com.example.semestralka.stat.Stat;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class PocasiService {

    private final PocasiRepository pocasiRepository;
    private final MestoRepository mestoRepository;

    Logger logger = LoggerFactory.getLogger(PocasiService.class);

    @Autowired
    public PocasiService(PocasiRepository pocasiRepository, MestoRepository mestoRepository) {
        this.pocasiRepository = pocasiRepository;
        this.mestoRepository = mestoRepository;
    }

    @Autowired
    private MongoOperations mongoOperations;



    public void addNewPocasi(Pocasi pocasi) {
        pocasi.setTime(LocalDateTime.now());
        pocasiRepository.save(pocasi);
    }

    public List<Show> getCurrentPocasi()
    {
        List<Mesto> ms = mestoRepository.findAll();
        List<Pocasi> ps;
        List<Show> r = new ArrayList<>();
        Pocasi np = null;
        for(Mesto m : ms)
        {
            ps = pocasiRepository.findPocasiByNameAndState(m.getName(),m.getState());
            LocalDateTime nearest = LocalDateTime.now().minusDays(14);

            for(Pocasi p : ps)
            {
                if(p.getTime().isAfter(nearest))
                {
                    nearest = p.getTime();
                    np =p;
                }
            }
            r.add(new Show(np.getName(), Timestamp.valueOf(np.getTime()),np.getTemp(),np.getPres(), np.getState()));
        }

        return r;
    }

    public List<Show> getAVGofPocasi(Integer interval)
    {
        List<Mesto> ms = mestoRepository.findAll();
        List<Pocasi> ps;
        List<Show> r = new ArrayList<>();
        double sumT = 0;
        int ammountT = 0;
        int sumP = 0;
        int ammountP = 0;
        for(Mesto m : ms)
        {
            ps = pocasiRepository.findPocasiByNameAndState(m.getName(),m.getState());
            LocalDateTime nearest = LocalDateTime.now().minusDays(interval);

            for(Pocasi p : ps)
            {
                if(p.getTime().isAfter(nearest))
                {
                    sumT = sumT + p.getTemp();
                    ammountT++;
                    sumP = sumP+p.getPres();
                    ammountP++;
                }
            }
            Double temp = sumT/ammountT;
            Integer pres = sumP/ammountP;
            r.add(new Show(m.getName(),Timestamp.valueOf(LocalDateTime.now()),temp,pres,m.getState()));
        }

        return r;
    }

    public Show getPocasiInCity(String name, String tag)
    {
        List<Pocasi> ps = pocasiRepository.findPocasiByNameAndState(name,tag);
        if(ps.isEmpty())logger.error("get pocasi in city: lokalita nenalezena: "+ name+" "+ tag);
        else {
            LocalDateTime nearest = LocalDateTime.now().minusDays(14);
            Pocasi np = null;
            for (Pocasi p : ps) {
                if (p.getTime().isAfter(nearest)) {
                    nearest = p.getTime();
                    np = p;
                }
            }
            return new Show(np.getName(),Timestamp.valueOf(np.getTime()),np.getTemp(),np.getPres(),np.getState());
        }
        return null;
    }

    public List<Pocasi> getExpired(LocalDateTime expDate)
    {
        return pocasiRepository.findByTimeBefore(expDate);
    }

    public void deleteExpired(LocalDateTime expDate) {
        List<Pocasi> ps = pocasiRepository.findByTimeBefore(expDate);
        if(ps.isEmpty()) logger.info("no expiring recorrds");
        else {
            for (Pocasi p : ps) {
                pocasiRepository.delete(p);

            }
        }
    }


    public void addPocasi(Pocasi pocasi) {
        Pocasi poc =  pocasiRepository.findPocasiByNameAndStateAndTime(pocasi.getName(), pocasi.getState(), pocasi.getTime());
        if(poc == null)
        {
            pocasiRepository.save(pocasi);
        }
        else logger.error("přidání počasí: počasí je již v daabázi "+ poc);
    }

    public void deletePocasi(Pocasi pocasi) {
        //Pocasi poc =  pocasiRepository.findPocasiByNameAndTimeAndSys(pocasi.getName(), pocasi.getTime(),pocasi.getSys());
        Optional<Pocasi> pocasiOptional = pocasiRepository.findPocasiByNameAndTime(pocasi.getName(),pocasi.getTime());
        if(!pocasiOptional.isPresent())
        {
            logger.error("mazání počasí: počasí "+ pocasiOptional+" neexistuje");
        }
        Long num = pocasiRepository.deleteByNameAndTime(pocasi.getName(),pocasi.getTime());
        pocasiRepository.findAll();


    }

    @Transactional
    public void updatePocasi(Pocasi pocasi,Double temp) {

        Pocasi p = pocasiRepository.findPocasiByNameAndStateAndTime(pocasi.getName(), pocasi.getState(), pocasi.getTime());
        if(p!=null) {
            Long num = pocasiRepository.deleteByNameAndTime(pocasi.getName(),pocasi.getTime());;
            pocasi.setTemp(temp);
            pocasiRepository.save(pocasi);
        }
        else logger.error("update počasí: neexistující záznam "+pocasi);

    }
    //TODO ošetřit správnost města + vytvořit nové město
    //TODO ošetřit hodnoty a vstup
    public void dataUpload(MultipartFile file) throws Exception
    {
        List<Pocasi> ps = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
        parseAllRecords.forEach(
                record -> {
                    Pocasi pocasi = new Pocasi();
                    pocasi.setName(record.getString("mesto"));
                    String dt = record.getString("cas");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                    LocalDateTime dateTime = LocalDateTime.parse(dt, formatter);
                    pocasi.setTime(dateTime);
                    pocasi.setTemp(record.getDouble("teplota"));
                    pocasi.setPres(record.getInt("tlak"));
                    pocasi.setState(record.getString("stat"));
                    ps.add(pocasi);
                }
        );
        pocasiRepository.saveAll(ps);

    }

    public void dataExport(Writer writer) throws IOException {
        List<Pocasi> ps = pocasiRepository.findAll();
        Collections.sort(ps, new CustomComparator());
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (Pocasi pocasi : ps) {
                csvPrinter.printRecord(pocasi.getName(),
                        pocasi.getTime(),
                        pocasi.getTemp(),
                        pocasi.getPres(),
                        pocasi.getState());
            }
        } catch (IOException e) {
            logger.error("Chyba při zápisu .CSV", e);
        }

    }

    public class CustomComparator implements Comparator<Pocasi> {
        @Override
        public int compare(Pocasi o1, Pocasi o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
