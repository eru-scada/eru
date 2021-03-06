/******************************************************************************
 * Copyright (c) 2017 Assemblits contributors                                 *
 *                                                                            *
 * This file is part of Eru The open JavaFX SCADA by Assemblits Organization. *
 *                                                                            *
 * Eru The open JavaFX SCADA is free software: you can redistribute it        *
 * and/or modify it under the terms of the GNU General Public License         *
 *  as published by the Free Software Foundation, either version 3            *
 *  of the License, or (at your option) any later version.                    *
 *                                                                            *
 * Eru is distributed in the hope that it will be useful,                     *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU General Public License for more details.                               *
 *                                                                            *
 * You should have received a copy of the GNU General Public License          *
 * along with Foobar.  If not, see <http://www.gnu.org/licenses/>.            *
 ******************************************************************************/
package org.assemblits.eru.entities;

import javafx.beans.property.*;
import org.assemblits.eru.util.MathUtil;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "tag", schema = "public")
public class Tag {
    /* ********** Static Fields ********** */
    private static final int DEFAULT_DECIMALS = 2;
    /* ********** Persistent Fields ********** */
    private final IntegerProperty id;
    private final StringProperty groupName;
    private final StringProperty name;
    private final BooleanProperty enabled;
    private final StringProperty description;
    private final IntegerProperty decimals;
    private final ObjectProperty<Type> type;
    private final ObjectProperty<Address> linkedAddress;
    private final ObjectProperty<Tag> linkedTag;
    private final StringProperty script;
    private final IntegerProperty mask;
    private final DoubleProperty scaleFactor;
    private final DoubleProperty scaleOffset;
    private final BooleanProperty alarmEnabled;
    private final StringProperty alarmScript;
    private final BooleanProperty historicalEnabled;
    private final StringProperty valueMap;

    /* ********** Dynamic Fields ********** */
    private final StringProperty value;
    private final ObjectProperty<Timestamp> timestamp;
    private final StringProperty status;
    private final BooleanProperty alarmed;

    /* ********** Constructors ********** */
    public Tag() {
        this.id = new SimpleIntegerProperty(this, "id");
        this.name = new SimpleStringProperty(this, "name", "");
        this.enabled = new SimpleBooleanProperty(this, "enabled", true);
        this.description = new SimpleStringProperty(this, "description", "");
        this.decimals = new SimpleIntegerProperty(this, "decimals", DEFAULT_DECIMALS);
        this.type = new SimpleObjectProperty<>();
        this.linkedAddress = new SimpleObjectProperty<>();
        this.linkedTag = new SimpleObjectProperty<>();
        this.script = new SimpleStringProperty(this, "script", "");
        this.mask = new SimpleIntegerProperty(this, "mask", 0);
        this.scaleFactor = new SimpleDoubleProperty(this, "scaleFactor", 1.0);
        this.scaleOffset = new SimpleDoubleProperty(this, "scaleOffset", 0.0);
        this.alarmEnabled = new SimpleBooleanProperty(this, "alarmEnabled", false);
        this.alarmScript = new SimpleStringProperty(this, "alarmScript", "");
        this.historicalEnabled = new SimpleBooleanProperty(this, "historicalEnabled", false);
        this.groupName = new SimpleStringProperty(this, "groupName", "");
        this.valueMap = new SimpleStringProperty(this, "valueMap", ""); // Map structure: "0.0=LOW 1.0=NORMAL 2.0=HIGH"
        this.value = new SimpleStringProperty(this, "value", "");
        this.timestamp = new SimpleObjectProperty<>(this, "timestamp", Timestamp.from(Instant.now()));
        this.status = new SimpleStringProperty(this, "status", "");
        this.alarmed = new SimpleBooleanProperty(this, "alarmed", false);
    }

    /* ********** Properties ********** */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Column(name = "enabled")
    public boolean getEnabled() {
        return enabled.get();
    }

    public void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    public BooleanProperty enabledProperty() {
        return enabled;
    }

    @Column(name = "description")
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    @Column(name = "decimals")
    public int getDecimals() {
        return decimals.get();
    }

    public void setDecimals(int decimals) {
        this.decimals.set(decimals);
    }

    public IntegerProperty decimalsProperty() {
        return decimals;
    }

    @Column(name = "tag_type")
    public String getTypeName() {
        return getType() == null ? "" : getType().name();
    }

    public void setTypeName(String name) {
        type.set(name == null || name.isEmpty() ? Type.INPUT : Type.valueOf(name));
    }

    @Transient
    public Type getType() {
        return type.get();
    }

    public void setType(Type type) {
        this.type.set(type);
    }

    public ObjectProperty<Type> typeProperty() {
        return type;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Address getLinkedAddress() {
        return linkedAddress.get();
    }

    public void setLinkedAddress(Address linkedAddress) {
        this.linkedAddress.set(linkedAddress);
    }

    public ObjectProperty<Address> linkedAddressProperty() {
        return linkedAddress;
    }

    @OneToOne
    public Tag getLinkedTag() {
        return linkedTag.get();
    }

    public void setLinkedTag(Tag linkedTag) {
        this.linkedTag.set(linkedTag);
    }

    public ObjectProperty<Tag> linkedTagProperty() {
        return linkedTag;
    }

    @Column(name = "script", length = 1024)
    public String getScript() {
        return script.get();
    }

    public void setScript(String script) {
        this.script.set(script);
    }

    public StringProperty scriptProperty() {
        return script;
    }

    @Column(name = "mask")
    public int getMask() {
        return mask.get();
    }

    public void setMask(int mask) {
        this.mask.set(mask);
    }

    public IntegerProperty maskProperty() {
        return mask;
    }

    @Column(name = "scale_factor")
    public double getScaleFactor() {
        return scaleFactor.get();
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor.set(scaleFactor);
    }

    public DoubleProperty scaleFactorProperty() {
        return scaleFactor;
    }

    @Column(name = "scale_offset")
    public double getScaleOffset() {
        return scaleOffset.get();
    }

    public void setScaleOffset(double scaleOffset) {
        this.scaleOffset.set(scaleOffset);
    }

    public DoubleProperty scaleOffsetProperty() {
        return scaleOffset;
    }

    @Column(name = "alarm_enabled")
    public boolean getAlarmEnabled() {
        return alarmEnabled.get();
    }

    public void setAlarmEnabled(boolean alarmEnabled) {
        this.alarmEnabled.set(alarmEnabled);
    }

    public BooleanProperty alarmEnabledProperty() {
        return alarmEnabled;
    }

    @Column(name = "alarm_script")
    public String getAlarmScript() {
        return alarmScript.get();
    }

    public void setAlarmScript(String alarmScript) {
        this.alarmScript.set(alarmScript);
    }

    public StringProperty alarmScriptProperty() {
        return alarmScript;
    }

    @Column(name = "historical_enabled")
    public boolean getHistoricalEnabled() {
        return historicalEnabled.get();
    }

    public void setHistoricalEnabled(boolean historicalEnabled) {
        this.historicalEnabled.set(historicalEnabled);
    }

    public BooleanProperty historicalEnabledProperty() {
        return historicalEnabled;
    }

    @Column(name = "group_name")
    public String getGroupName() {
        return groupName.get();
    }

    public void setGroupName(String groupName) {
        this.groupName.set(groupName);
    }

    public StringProperty groupNameProperty() {
        return groupName;
    }

    @Column(name = "value_map")
    public String getValueMap() {
        return valueMap.get();
    }

    public StringProperty valueMapProperty() {
        return valueMap;
    }

    public void setValueMap(String valueMap) {
        this.valueMap.set(valueMap);
    }

    @Transient
    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        try {
            // Apply Mask, Scale, and Round
            final int rawValue = Integer.parseInt(value);
            final double maskedValue = getMask() == 0 ? rawValue : rawValue & getMask();
            final double scaledValue = maskedValue * getScaleFactor() + getScaleOffset();
            final double roundedValue = MathUtil.round(scaledValue, getDecimals());
            String finalValue = String.valueOf(roundedValue);

            // Look if there is a value Map
            if (!getValueMap().isEmpty()) {
                final String[] tokens = getValueMap().split(" |=");
                final Map<String, String> map = new HashMap<>();
                for (int i=0; i<tokens.length-1; ) map.put(tokens[i++], tokens[i++]);
                if (map.containsKey(finalValue)){
                    finalValue = map.get(finalValue);
                }
            }

            this.value.set(finalValue);
        } catch (NumberFormatException e) {
            // Is not a number
            this.value.set(value);
        } catch (Exception e) {
            // Is an error
            this.status.set(e.getLocalizedMessage());
            this.value.set("????");
            e.printStackTrace();
        }
    }

    public StringProperty valueProperty() {
        return value;
    }

    @Transient
    public Timestamp getTimestamp() {
        return timestamp.get();
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp.set(timestamp);
    }

    public ObjectProperty<Timestamp> timestampProperty() {
        return timestamp;
    }

    @Transient
    public String getStatus() {
        return status.get();
    }

    public void setStatus(String statusName) {
        this.status.set(statusName);
    }

    public StringProperty statusProperty() {
        return status;
    }

    @Transient
    public boolean getAlarmed() {
        return alarmed.get();
    }

    public void setAlarmed(boolean alarmed) {
        this.alarmed.set(alarmed);
    }

    public BooleanProperty alarmedProperty() {
        return alarmed;
    }

    @Override
    public String toString() {
        return getGroupName()+":"+getName();
    }

    public enum Type {INPUT, SCRIPT, OUTPUT, LOGICAL}

}