package com.citic.source.canal;

import static com.citic.source.canal.CanalSourceConstants.USE_AVRO;

import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.citic.source.canal.core.AbstractCanalSource;
import com.citic.source.canal.core.EntryConverterInterface;
import com.citic.source.canal.resolve.EntryConverter;
import java.util.List;
import org.apache.flume.Context;
import org.apache.flume.Event;

public class CanalSource extends AbstractCanalSource {

    @Override
    protected EntryConverterInterface newEntryConverterInstance(Context context,
        CanalConf canalConf) {
        boolean useAvro = context.getBoolean(USE_AVRO, true);
        return new EntryConverter(useAvro, canalConf);
    }

    @Override
    protected void handleCanalEntry(Entry entry, CanalConf canalConf, List<Event> eventsAll,
        EntryConverterInterface entryConverter) {
        List<Event> events = entryConverter.convert(entry, canalConf);
        eventsAll.addAll(events);
    }
}
