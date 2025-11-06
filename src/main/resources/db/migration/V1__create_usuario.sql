-- Drop tables for a clean baseline (SQL Server)
IF OBJECT_ID('dbo.ordem_servico', 'U') IS NOT NULL
    DROP TABLE dbo.ordem_servico;

IF OBJECT_ID('dbo.moto', 'U') IS NOT NULL
    DROP TABLE dbo.moto;

IF OBJECT_ID('dbo.patio', 'U') IS NOT NULL
    DROP TABLE dbo.patio;

IF OBJECT_ID('dbo.usuario', 'U') IS NOT NULL
    DROP TABLE dbo.usuario;