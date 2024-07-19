/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 */
package org.eclipse.daanse.rolap.mapping.example.tutorial.basic.cube.minimal;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.RolapContextMappingSupplier;
import org.eclipse.daanse.rolap.mapping.api.model.RolapContextMapping;
import org.eclipse.daanse.rolap.mapping.instance.api.Kind;
import org.eclipse.daanse.rolap.mapping.instance.api.MappingInstance;
import org.eclipse.daanse.rolap.mapping.instance.api.Source;
import org.eclipse.daanse.rolap.mapping.pojo.CatalogMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.DocumentationMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.MeasureGroupMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.PhysicalCubeMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.RolapContextMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SchemaMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.SumMeasureMappingImpl;
import org.eclipse.daanse.rolap.mapping.pojo.TableQueryMappingImpl;
import org.osgi.service.component.annotations.Component;

@MappingInstance(kind = Kind.TUTORIAL, source = Source.POJO, number = "2")
@Component
public class ExampleSupplier implements RolapContextMappingSupplier {

    private final static String name = "Minimal Physical Cube";

    private final static String documentation_text = """
            Context of a minimal physical cube with just one Measure but no other dimensions.

            This example schema contains one cube named "CubeOneMeasure".

            A physical cube is based on a Database Table mostly called `Fact-Table` which refers to a database table containing one or more measurements to be aggregated.
            In this case the database table representing the fact table is named "Fact" in the database, which is adressed in the name attribute of the TableQuery.

            Each measurement of the cube is defined in a separate and grouped by an MeasureGroup.
            The measurement in this example cube is named "Measure-Sum" (name attribute). It corresponds to the "VALUE" column (column attribute) in the database table "Fact" and is aggregated by summation (aggregator attribute).

            The MeasureGroup can but must not have a Name.
            """;

    private final static DocumentationMappingImpl documentation = new DocumentationMappingImpl(documentation_text);

    private final static TableQueryMappingImpl tableQuery = TableQueryMappingImpl.builder().withName("Fact").build();

    private final static SumMeasureMappingImpl measure = SumMeasureMappingImpl.builder()
            .withName("Measure-Sum")
            .withColumn("VALUE")
            .build();

    private final static MeasureGroupMappingImpl measureGroup = MeasureGroupMappingImpl.builder()
            .withMeasures(List.of(measure))
            .build();

    private final static PhysicalCubeMappingImpl cube = PhysicalCubeMappingImpl.builder()
            .withName("CubeOneMeasure")
            .withQuery(tableQuery)
            .withMeasureGroups(List.of(measureGroup))
            .withDocumentation(new DocumentationMappingImpl(""))
            .build();

    private final static SchemaMappingImpl schema = SchemaMappingImpl.builder()
            .withName("AnySchemaName")
            .withCubes(List.of(cube))
            .build();

    private final static CatalogMappingImpl catalog = CatalogMappingImpl.builder()
            .withName("AnyCatalogName")
            .withSchemas(List.of(schema))
            .build();

    @Override
    public RolapContextMapping get() {
        return RolapContextMappingImpl.builder()
                .withName(name)
                .withDocumentation(documentation)
                .withCatalogs(List.of(catalog))
                .build();
    }

}
