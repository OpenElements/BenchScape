import { render, screen } from "@testing-library/react";
import { describe, it, vi, expect } from "vitest";
import { mockEnvironmentsResponse } from "../../../__mocks__";
import EnvironmentsPage from ".";

const mockedUseNavigate = vi.fn();

vi.mock("react-router-dom", () => {
  const actualRouterDom = vi.importActual("react-router-dom");

  return {
    ...actualRouterDom,
    useNavigate: () => mockedUseNavigate,
    Link: ({ to, children }) => <a href={to}>{children}</a>,
  };
});

vi.mock("../../hooks", () => ({
  useEnvironments: vi.fn().mockReturnValue({
    data: mockEnvironmentsResponse,
    mutate: vi.fn(),
  }),
  useForOsFamilyFilter: vi.fn().mockReturnValue({
    data: ["Windows", "Mac", "Linux"],
  }),
  useEnvironmentMetadata: vi.fn().mockReturnValue({
    data: ["x86_64", "arm64"],
  }),
}));

describe("Environment master page", () => {
  it("should display all available environments", () => {
    render(<EnvironmentsPage />);
    const expectedLabels = [
      "OS Family",
      "OS Version",
      "Architecture",
      "CPU Cores",
      "Memory",
      "JVM Name",
      "JVM Version",
      "JMH Version",
    ];

    const expectedHeaders = [
      "Name",
      "Operating System",
      "Hardware",
      "JAVA",
      "JMH",
      "",
    ];

    const labels = screen
      .getAllByRole("label")
      .map((label) => label.textContent);
    const headers = screen
      .getAllByRole("columnheader")
      .map((header) => header.textContent);

    expectedLabels.forEach((label) => {
      expect(labels).toContain(label);
    });

    expectedHeaders.forEach((header) => {
      expect(headers).toContain(header);
    });

    const table = screen.getByTestId("environments-table");
    expect(table).toBeVisible();
    expect(screen.getByText("benchScape-main-mac-14")).toBeInTheDocument();
  });
});
